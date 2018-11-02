package com.example.x2y.englishapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

public class WordProgressActivity extends AppCompatActivity {
    private ColumnChartView columnChart;
    private LineChartView lineChart;
    //X轴标注
    String[]date = {"10-31","11-31","12-31","1-31","2-31","3-31","4-31","5-31","6-31","7-31"};
    //图标的数据点
    int[]count = {50,100,100,200,250,300,300,350,400,500};
    private List<PointValue>mPointValues = new ArrayList<PointValue>();
    private List<AxisValue>mAxisValues = new ArrayList<AxisValue>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_progress);

        //返回图标事件，结束当前活动
        View backbutton = (View)findViewById(R.id.word_progress_back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lineChart =(LineChartView)findViewById(R.id.line_chart);
        columnChart = (ColumnChartView)findViewById(R.id.column_chart);
        getAxisXLable();//获取X轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化线性图标
        initColumnChart();//初始化柱状图
    }
    //设置x轴的显示
    private void getAxisXLable(){
        for(int i = 0;i < date.length;i++){
            mAxisValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }
    //图标每个点的显示
    private void getAxisPoints(){
        for(int i= 0;i < count.length;i++){
        mPointValues.add(new PointValue(i,count[i]));
    }
}
    //初始化线性图表
    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#90EE90"));//折线颜色 亮绿
        List<Line>lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状
        line.setCubic(false);//曲线是否平滑
        line.setFilled(true);//是否填充曲线面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLines(true);//是否用线显示 如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(false);//X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);//字体颜色
       // axisX.setName("进步曲线");
        axisX.setTextSize(10);
        axisX.setMaxLabelChars(8);//最多几个x轴坐标 缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisValues);//填充X轴的坐标名称
        data.setAxisXBottom(axisX);//x轴在底部
        axisX.setHasLines(true);//x的分割线

        //Y轴是根据数据的大小自动设置y轴上限
        Axis axisY = new Axis();
        axisY.setName("");
        axisY.setTextSize(10);
        data.setAxisYLeft(axisY);//Y轴设置在左边

        //设置行为属性，支持缩放滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float)2);//最多缩放比例
        lineChart.setContainerScrollEnabled(true,ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        //设置x轴显示数据的个数
        //不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);

    }

    //初始化柱状图
    private void initColumnChart(){
        //测试数据
        int[]count2 = {50,100,100,200,250,300,300};
      //一个柱状图需要的柱子集合
      List<Column>columnList = new ArrayList<Column>();
      //每根柱子分为多根柱子
      List<SubcolumnValue>subcolumnValueList;

      int columns = 7;//一共七根柱子
      int subColumn = 1;//每根柱子的子柱子为1根
      for (int i = 0;i<columns;i++){
          subcolumnValueList = new ArrayList<>();
          for(int j = 0;j<subColumn;j++){
              //每根柱子需要一个颜色  填充数据这里要独立成函数满足后续需求
              subcolumnValueList.add(new SubcolumnValue(count2[i],ChartUtils.pickColor()));
          }
          //每个柱子需要一个子柱子集合
          Column column = new Column(subcolumnValueList);
          //让圆柱能标注数据 若要带小数需要格式化
          column.setHasLabels(true);
          columnList.add(column);
      }
      //填充数据到data
      ColumnChartData data = new ColumnChartData(columnList);
      Axis axisX = new Axis();
      Axis axisY = new Axis();
      axisY.setTextSize(10);
      axisX.setTextSize(10);
      //设置XY名字 这里不设置
      axisX.hasLines();
      axisY.hasLines();//  显示网格线
      axisX.setTextColor(Color.GRAY);

      //给轴设置值
        List<AxisValue>list = new ArrayList<>();
        for(int i = 0;i<columns;i++){
            list.add(new AxisValue(i).setLabel("周"+(i+1)));//i代表位置
        }
        //给x设置值
        axisX.setValues(list);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);//Y轴设置在左边
        //设置是否让多根柱子在一根上显示
        data.setStacked(false);
        columnChart.setColumnChartData(data);//填充数据
        columnChart.setZoomEnabled(false);
       // columnChart.setMaxZoom((float)2);//最多缩放比例
        columnChart.setInteractive(true);//设置平滑
        columnChart.setContainerScrollEnabled(true,ContainerScrollType.HORIZONTAL);
        columnChart.setZoomType(ZoomType.HORIZONTAL);
        columnChart.setVisibility(View.VISIBLE);

    }

}
