package com.example.bpapp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.bpapp.bpapp.R;
import com.example.bpapp.broadcast.ReceiveBroadCast;
import com.example.bpapp.entity.Data;
import com.example.bpapp.entity.Msglist;
import com.example.bpapp.service.ClientService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * 首页Activity
 * Created by 宁润 on 2017/5/28.
 */
public class IndexActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SwipeRefreshLayout.OnRefreshListener,ReceiveBroadCast.BRInteraction {
    private Spinner spinner;
    private List<String> optionList;
    private List<Data> dataList;
    private ArrayAdapter<String> arr_adapter;
    private LineChartView lineChart;
    private ListView listView;
    private Button settingButton;
    private SwipeRefreshLayout swipeLayout;


    String[] date =null;
    int[] highpressure_score=null;
    int[] lowpressure_score=null;
    int[] heartbeat_score=null;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();


    String[] from={"date","highpressure_score","lowpressure_score","heartbeat_score"};              //这里是ListView显示内容每一列的列名
    int[] to={R.id.date,R.id.highpressure_score,R.id.lowpressure_score,R.id.heartbeat_score};   //这里是ListView显示每一列对应的list_item中控件的id
    String[] highpressure_scoreStr=null;
    String[] lowpressure_scoreStr=null;
    String[] heartbeat_scoreStr=null;
    ArrayList<HashMap<String,String>> list=null;
    HashMap<String,String> map=null;

    private ReceiveBroadCast receiveBroadCast;
    private ClientService clientService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.index_frame);
        lineChart = (LineChartView)findViewById(R.id.line_chart);
        settingButton=(Button)findViewById(R.id.toolbar_right_btn2);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        swipeLayout.setOnRefreshListener(this);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IndexActivity.this,SettingActivity.class);
//                intent.putExtra();
                startActivity(intent);
            }
        });

        clientService=ClientService.getInstance();
        dataList=new ArrayList<>();
        dataList.addAll(Msglist.getDataList());//一开始数据是从登录后获得的初始数据

        receiveBroadCast=new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("DH");    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);
        receiveBroadCast.setBRInteractionListener(this);


        initSpinner();//初始化下拉列表视图
        initDataView();//初始化数据显示视图，包括折线图和表格


    }

    private void initDataView() {
        initData();//初始化数据
        initChart(0);//初始化折线图，默认显示高血压的折线图
        initDataList();//初始化表格
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 1:
//                    mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
//                    mAdapter.notifyDataSetChanged();
                    //数据更新操作
                    initDataView();//更新数据显示视图
                    swipeLayout.setRefreshing(false);
                    break;

            }
        }
    };

    /**
     * 初始化页面数据
     */
    private void initData() {

        System.out.println("get data success!");
        for(Data each:dataList){
            System.out.println(each.getHighscore()+" "+each.getLowscore()+" "+each.getHeartbeat()+" "+each.getDatatime());
        }

        int datalen=dataList.size();
        date=null;
        highpressure_score=null;
        lowpressure_score=null;
        heartbeat_score=null;
        highpressure_scoreStr=null;
        lowpressure_scoreStr=null;
        heartbeat_scoreStr=null;

        date=new String[datalen];
        highpressure_score=new int[datalen];
        lowpressure_score=new int[datalen];
        heartbeat_score=new int[datalen];
        highpressure_scoreStr=new String[datalen];
        lowpressure_scoreStr=new String[datalen];
        heartbeat_scoreStr=new String[datalen];

        for(int i=0;i<datalen;i++){
            date[i]=dataList.get(i).getDatatime();//X轴的日期
            highpressure_score[i]=dataList.get(i).getHighscore();//高血压数据点
            lowpressure_score[i]=dataList.get(i).getLowscore();//低血压数据点
            heartbeat_score[i]=dataList.get(i).getHeartbeat();//心率数据点

            highpressure_scoreStr[i]=String.valueOf(highpressure_score[i]);
            lowpressure_scoreStr[i]=String.valueOf(lowpressure_score[i]);
            heartbeat_scoreStr[i]=String.valueOf(heartbeat_score[i]);
        }

    }

    /**
     * 初始化列表
     */
    private void initDataList() {
        listView=(ListView) findViewById(R.id.dataView);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeLayout.setEnabled(true);
                else
                    swipeLayout.setEnabled(false);
            }
        });
//创建ArrayList对象；
        list=new ArrayList<HashMap<String,String>>();
        //将数据存放进ArrayList对象中，数据安排的结构是，ListView的一行数据对应一个HashMap对象，
        //HashMap对象，以列名作为键，以该列的值作为Value，将各列信息添加进map中，然后再把每一列对应
        //的map对象添加到ArrayList中

        for(int i=0; i<date.length; i++){
            map=new HashMap<String,String>();       //为避免产生空指针异常，有几列就创建几个map对象
            map.put("date", date[i]);
            map.put("highpressure_score", highpressure_scoreStr[i]);
            map.put("lowpressure_score", lowpressure_scoreStr[i]);
            map.put("heartbeat_score", heartbeat_scoreStr[i]);
            list.add(map);
        }
        //创建一个SimpleAdapter对象
        SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.list_item,from,to);
        //调用ListActivity的setListAdapter方法，为ListView设置适配器
        listView.setAdapter(adapter);
    }

    /**
     * 初始化图表
     */
    private void initChart(int type) {
        getAxisXLables();//获取x轴的标注
        getAxisPoints(type);//获取坐标点
        initLineChart(type);//初始化折线图
    }

    /**
     * 初始化下拉列表
     */
    private void initSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        //数据
        optionList = new ArrayList<String>();
        optionList.add("高血压");
        optionList.add("低血压");
        optionList.add("心率");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionList);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //加载适配器
        spinner.setAdapter(arr_adapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(this);

        //设置默认值
        spinner.setVisibility(View.VISIBLE);
    }

    /**
     * 下拉列表监听事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        refreshUI();
        switch(position){
            //高血压
            case 0:
                //更新图表
                initChart(0);
                break;
            //低血压
            case 1:
                //更新图表
                initChart(1);
                break;
            //心率
            case 2:
                //更新图表
                initChart(2);
                break;

        }

    }

    private void refreshUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                lineChart.postInvalidate();
            }
        }).start();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onRefresh() {
        String message=clientService.refreshData();
        List<Data> list=clientService.parseData(message);
        dataList.addAll(list);

        mHandler.sendEmptyMessageDelayed(1, 2000);//刷新完成

    }
    /**
     * 设置X轴的显示
     */
    private void getAxisXLables() {
        // TODO Auto-generated method stub
        mAxisXValues.clear();
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }


    /**
     * 图表每个点的显示
     */
    private void getAxisPoints(int type) {
        // TODO Auto-generated method stub
        mPointValues.clear();
        switch(type){
            case 0:
                for (int i = 0; i < highpressure_score.length; i++) {
                    mPointValues.add(new PointValue(i, highpressure_score[i]));
                }
                break;
            case 1:
                for (int i = 0; i < lowpressure_score.length; i++) {
                    mPointValues.add(new PointValue(i, lowpressure_score[i]));
                }
                break;
            case 2:
                for (int i = 0; i < heartbeat_score.length; i++) {
                    mPointValues.add(new PointValue(i, heartbeat_score[i]));
                }
                break;
            default:
                for (int i = 0; i < highpressure_score.length; i++) {
                    mPointValues.add(new PointValue(i, highpressure_score[i]));
                }
                break;

        }

    }



    /**
     * 初始化折线图的显示
     */
    private void initLineChart(int type) {
        // TODO Auto-generated method stub

        Line line = new Line(mPointValues).setColor(Color.parseColor("#DB7093"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data =new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        axisX.setName("日期");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        if(type==0){
            axisY.setName("高血压值");//y轴标注
        }else if(type==1){
            axisY.setName("低血压值");//y轴标注
        }else if(type==2){
            axisY.setName("心率值");//y轴标注
        }
        axisY.setTextSize(10);//设置字体大小
        axisY.setTextColor(Color.BLACK);  //设置字体颜色
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);
    }


    @Override
    public void setMsg(String content) {
        if(content!=null){
            System.out.println("data content:"+content);
            List<Data> list=clientService.parseData(content);
            dataList.addAll(list);
//            for(int i=0;i<dataList.size();i++){
//                System.out.println(dataList.get(i).getHighscore()+" "+dataList.get(i).getLowscore()+" "
//                +dataList.get(i).getHeartbeat()+" "+dataList.get(i).getDatatime());
//            }
            initDataView();//更新数据显示视图
        }
    }
}
