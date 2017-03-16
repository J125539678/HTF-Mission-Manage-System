package htf.htfmms.Analysis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import htf.htfmms.Database.MissionCount;
import htf.htfmms.R;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class Chart extends AppCompatActivity {

    private Button button1,button2,button3;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    List<MissionCount> CountList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Context context = Chart.this;

        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        button3 = (Button) findViewById(R.id.btn3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回箭头
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(Chart.this,AnalysisActivity.class);
                startActivity(toMain);
            }
        });

        BmobUser user = BmobUser.getCurrentUser(context);
        BmobQuery<MissionCount> query = new BmobQuery<MissionCount>();
        query.addWhereEqualTo("Author", user);    // 查询当前用户的所有任务统计
        query.order("-Date");// 按照时间降序
        //query.setLimit(30);//最多返回30条数据
        query.findObjects(context, new FindListener<MissionCount>() {

            @Override
            public void onSuccess(List<MissionCount> Counts) {
                // TODO Auto-generated method stub
                //将结果显示在列表Counts中
                //此处改写
                CountList = Counts;
                List<PointValue> values = new ArrayList<PointValue>();
                List x_list = new ArrayList();

                for (int i=CountList.size()-1; i>=0;--i)
                {
                    values.add(new PointValue(CountList.size()-i-1,Integer.parseInt(CountList.get(i).getGainPointToday())));
                    AxisValue value = new AxisValue((float)(CountList.size()-i-1),initDate(CountList.get(i).getDate()));
                    x_list.add(value);
                }


                drawChart(values,x_list,0,1);

            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                //showErrorView(0);
            }
        });

        View.OnClickListener mylistener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //这里可以判断点击的是哪个对象，通过v对象的getId()方法获取当前点击对象的id
                switch (v.getId()) {
                    case R.id.btn1:
                        //
                        //LineChartView chart = (LineChartView)findViewById(R.id.chart);

                        List<PointValue> values1 = new ArrayList<PointValue>();
                        List x_list1 = new ArrayList();
                        for (int i=CountList.size()-1; i>=0;--i)
                        {
                            values1.add(new PointValue(CountList.size()-i-1,Integer.parseInt(CountList.get(i).getGainPointToday())));
                            AxisValue value1 = new AxisValue((float)CountList.size()-i-1,initDate(CountList.get(i).getDate()));
                            x_list1.add(value1);
                        }
                        drawChart(values1,x_list1,0,1);

                        break;
                    case R.id.btn2:
                        //
                        List<PointValue> values2 = new ArrayList<PointValue>();
                        List<PointValue> values3 = new ArrayList<PointValue>();
                        List x_list2 = new ArrayList();


                        for (int i=CountList.size()-1; i>=0;--i)
                        {
                            values2.add(new PointValue(CountList.size()-i-1,Integer.parseInt(CountList.get(i).getAccomplishToday())));
                            values3.add(new PointValue(CountList.size()-i-1,Integer.parseInt(CountList.get(i).getFailToday())));
                            AxisValue value2 = new AxisValue((float)CountList.size()-i-1,initDate(CountList.get(i).getDate()));
                            x_list2.add(value2);
                        }

                        drawChart2Line(values2,values3,x_list2);


                        break;
                    case R.id.btn3:

                        List<PointValue> values4 = new ArrayList<PointValue>();
                        List x_list4 = new ArrayList();
                        for (int i=CountList.size()-1; i>=0;--i)
                        {
                            values4.add(new PointValue(CountList.size()-i-1,
                                    (float)Integer.parseInt(CountList.get(i).getAccomplishToday())  /
                                            (float)(Integer.parseInt(CountList.get(i).getFailToday())+Integer.parseInt(CountList.get(i).getAccomplishToday()))
                            ));
                            AxisValue value4 = new AxisValue((float)CountList.size()-i-1,initDate(CountList.get(i).getDate()));
                            x_list4.add(value4);
                        }
                        drawChart(values4,x_list4,1,3);

                        //
                        break;
                    default:
                        break;
                }
            }
        };
        button1.setOnClickListener(mylistener);
        button2.setOnClickListener(mylistener);
        button3.setOnClickListener(mylistener);

        // LineChartView chart = new LineChartView(context);

        //layout.addView(chart);
        //LineChartView chart = (LineChartView)findViewById(R.id.chart);



    }

    char[] initDate(String date) {
        return (date.substring(4,6)+"."+date.substring(6,8)).toCharArray();
    }

    public void drawChart2Line (List<PointValue> values1,List<PointValue> values2,List x_list){
        Line line1 = new Line(values1).setColor(Color.GREEN).setCubic(true);
        line1.setHasLabels(true);
        line1.setCubic(false);
        line1.setPointRadius(2);

        Line line2 = new Line(values2).setColor(Color.RED).setCubic(true);
        line2.setHasLabels(true);
        line2.setCubic(false);
        line2.setPointRadius(2);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line1);
        lines.add(line2);

        LineChartData data = new LineChartData();
        data.setLines(lines);
        //data.setValueLabelBackgroundColor(Color.BLUE);

        int max = -1000000;
        int min = 1000000;
        for (int i = 0;i<values1.size();++i)
        {
            int x = (int)values1.get(i).getY();
            if (x>max) max = x;
            if (x<min) min = x;
        }
        for (int i = 0;i<values2.size();++i)
        {
            int x = (int)values2.get(i).getY();
            if (x>max) max = x;
            if (x<min) min = x;
        }
        int delta = (int)((max-min)*0.1);
        if (delta==0) delta = 1;


        if (hasAxes) {
            Axis axisX = new Axis().setHasLines(true);// 实例化一个Axis
            axisX.setValues(x_list); //设定x轴的值
            axisX.setHasTiltedLabels(false);
            axisX.setTextSize(15);//设置字体大小
            axisX.setMaxLabelChars(10);
            data.setAxisXBottom(axisX); // data 是我全局的 ColumnChartData data;
            Axis axisY = new Axis();//.setHasLines(true);

            //axisX.setMaxLabelChars(3);
            if (hasAxesNames) {
                axisX.setName("日期");
                axisY.setName("成功/失败任务数量");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        // LineChartView chart = new LineChartView(context);
        LineChartView chart = (LineChartView)findViewById(R.id.chart);
        chart.setLineChartData(data);
        chart.setInteractive(true);
        chart.setZoomType(ZoomType.HORIZONTAL);
        //chart.setMaxZoom((float) 2);//最大方法比例
        chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        chart.setLineChartData(data);
        chart.setVisibility(View.VISIBLE);
        Viewport v = new Viewport(chart.getMaximumViewport());
        v.left = 0;
        v.right= 3;
        chart.setCurrentViewport(v);

        Viewport v2 = new Viewport(chart.getMaximumViewport());
        v2.top =max+delta; //example max value
        v2.bottom = min-delta;
        chart.setMaximumViewport(v2);
    }
    public void drawChart(List<PointValue> values,List x_list,int decimals,int choose)
    {
        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.GREEN).setCubic(true);
        line.setHasLabels(true);
        line.setCubic(false);
        line.setPointRadius(2);
        if (decimals != 0)
            line.setFormatter(new SimpleLineChartValueFormatter(decimals));
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);
        data.setValueLabelBackgroundColor(Color.BLUE);

        int max = -1000000;
        int min = 1000000;
        for (int i = 0;i<values.size();++i)
        {
            int x = (int)values.get(i).getY();
            if (x>max) max = x;
            if (x<min) min = x;
        }
        int delta = (int)((max-min)*0.1);
        if (delta==0) delta = 1;


        if (hasAxes) {
            Axis axisX = new Axis().setHasLines(true);// 实例化一个Axis
            axisX.setValues(x_list); //设定x轴的值
            axisX.setHasTiltedLabels(false);
            axisX.setTextSize(15);//设置字体大小
            axisX.setMaxLabelChars(10);
            data.setAxisXBottom(axisX); // data 是我全局的 ColumnChartData data;
            Axis axisY = new Axis();//.setHasLines(true);
            axisY.setTextSize(15);//设置字体大小

            //axisX.setMaxLabelChars(3);
            if (hasAxesNames) {
                axisX.setName("日期");
                switch (choose) {
                    case 1:axisY.setName("分数");break;
                    case 2:axisY.setName("成功/失败任务数量");break;
                    case 3:axisY.setName("完成率");break;
                    default:break;
                }

            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        // LineChartView chart = new LineChartView(context);
        LineChartView chart = (LineChartView)findViewById(R.id.chart);
        chart.setLineChartData(data);
        chart.setInteractive(true);
        chart.setZoomType(ZoomType.HORIZONTAL);
        //chart.setMaxZoom((float) 2);//最大方法比例
        chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        chart.setLineChartData(data);
        chart.setVisibility(View.VISIBLE);
        Viewport v = new Viewport(chart.getMaximumViewport());
        v.left = 0;
        v.right= 3;
        chart.setCurrentViewport(v);

        Viewport v2 = new Viewport(chart.getMaximumViewport());
        v2.top =max+delta; //example max value
        v2.bottom = min-delta;
        chart.setMaximumViewport(v2);

        //example min value


//Optional step: disable viewport recalculations, thanks to this animations will not change viewport automatically.
        //chart.setViewportCalculationEnabled(false);
    }
}
