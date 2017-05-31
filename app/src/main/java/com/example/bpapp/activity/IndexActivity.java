package com.example.bpapp.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.bpapp.bpapp.R;

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


public class IndexActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private LineChartView lineChart;
    private ListView listView;
    private LinearLayout linearLayoutRoot;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        TextView textview = new TextView(this);
//        textview.setText("这是首页");
//        setContentView(textview);
        setContentView(R.layout.index_frame);
        linearLayoutRoot=(LinearLayout) findViewById(R.id.linearlayoutroot);
        lineChart = (LineChartView)findViewById(R.id.line_chart);
        initData();
        initSpinner();
        initChart(0);
        initDataList();


    }

    /**
     * 初始化页面数据
     */
    private void initData() {
        String[] date2={"10-22","11-22","12-22","1-22","6-22","5-23","5-22","6-22","5-23","5-22"};
        int[] highpressure_score2={50,42,90,33,10,74,22,18,79,20};
        int[] lowpressure_score2={40,32,80,23,5,64,12,5,50,12};
        int[] heartbeat_score3={100,80,85,70,70,74,75,80,85,80};
        int datalen=date2.length;
        date=new String[datalen];
        highpressure_score=new int[datalen];
        lowpressure_score=new int[datalen];
        heartbeat_score=new int[datalen];
        highpressure_scoreStr=new String[datalen];
        lowpressure_scoreStr=new String[datalen];
        heartbeat_scoreStr=new String[datalen];
        
        date = date2;//X轴的标注
        highpressure_score= highpressure_score2;//高血压数据点
        lowpressure_score= lowpressure_score2;//低血压数据点
        heartbeat_score= heartbeat_score3;//心率数据点

        for(int i=0;i<date.length;i++){
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

        lineChart=(LineChartView) findViewById(R.id.line_chart);
        
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
        data_list = new ArrayList<String>();
        data_list.add("高血压");
        data_list.add("低血压");
        data_list.add("心率");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
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
        switch(position){
            //高血压
            case 0:
                //更新图表
//                linearLayoutRoot.removeViewAt(1);
//                initChart(0);
                break;
            //低血压
            case 1:
                //更新图表
               // linearLayoutRoot.removeViewAt(1);
                initChart(1);
               // linearLayoutRoot.addView(lineChart,1);
                break;
            //心率
            case 2:
                //更新图表
               // linearLayoutRoot.removeViewAt(1);
                initChart(2);
               // linearLayoutRoot.addView(lineChart,1);
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 设置X轴的显示
     */
    private void getAxisXLables() {
        // TODO Auto-generated method stub
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }


    /**
     * 图表每个点的显示
     */
    private void getAxisPoints(int type) {
        // TODO Auto-generated method stub
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
        lineChart=(LineChartView) findViewById(R.id.line_chart);
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
        LineChartData data = new LineChartData();
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
        v.right= 10;
        lineChart.setCurrentViewport(v);
    }



}
