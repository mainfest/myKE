package com.example.xeby.myke;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.xeby.myke.Base.BaseActivity;
import com.example.xeby.myke.Base.GridAda;
import com.example.xeby.myke.Base.ToastUtils;
import com.example.xeby.myke.Utils.SharePreferenceUntil;
import com.example.xeby.myke.Utils.myDate;
import com.example.xeby.myke.activity.LoginActivity;
import com.example.xeby.myke.database.dao.StuCourseDao;
import com.example.xeby.myke.entity.Classs;
import com.example.xeby.myke.entity.Course;
import com.example.xeby.myke.net.CourseService;
import com.example.xeby.myke.net.HttpConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.xeby.myke.Utils.myDate.initCal;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_LOGIN = 0;
    //    private static final String [] TITLE_DATA = {"课程","周一","周二","周三","周四","周五","周六","周日"};
//    private static final int GRID_ROW_COUNT = 11;
//    private static final int GRID_COL_COUNT = 8;
    private List<Classs> mStuCourseList = new ArrayList<>();
    private CourseService mCourseService;
    private StuCourseDao mStuCourseDao;
    //    private GridLayout mGlClsTitle;
//    private GridLayout mGlClsContent;
    private RecyclerView mRvKe;

    private GridAda adapter;
    private Classs[] classList = new Classs[77];

    //    private int mTableDistance;
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        mStuCourseDao = StuCourseDao.getInstance(this);
        setContentView(R.layout.main_view);
//        initData();
    }

    private void initData() {
        myDate.initCal();
        List<Classs> courses = mStuCourseDao.getStuClsList();
            mStuCourseList.addAll(courses);
            for (Classs c : mStuCourseList) {
                if (myDate.getWeekSpan()>=c.getStartWeek()&&myDate.getWeekSpan()<=c.getEndWeek())
                    if (c.getType()==1&&myDate.getWeekSpan()%2==1||c.getType()==2&&myDate.getWeekSpan()%2==0||c.getType()==0)
                        classList[c.getDay() + c.getStart() * 7] = c;
        }

    }

    private void initRv() {
        mRvKe = getViewById(R.id.rv_kebiao);
        getWindow().setBackgroundDrawableResource(R.drawable.bac);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRvKe.setLayoutManager(staggeredGridLayoutManager);
        adapter = new GridAda(this, classList);
        adapter.notifyItemInserted(70);
        adapter.notifyDataSetChanged();
        mRvKe.setAdapter(adapter);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        ToastUtils.init(this);
        mCourseService = new CourseService();
    }


//    //设置表格显示星期的地方
//    private void setUpClsTitle(){
//        for (int i=0; i<TITLE_DATA.length; ++i){
//            String content = TITLE_DATA[i];
//            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//            //第一列的时候
//            if (i == 0){
//                params.width = mTableDistance;
//            }
//            else {
//                //添加分割线
//                View divider = getLayoutInflater().inflate(R.layout.grid_title_form,mGlClsTitle,false);
//                mGlClsTitle.addView(divider);
//
//                params.width = mTableDistance * 2;
//            }
//            params.height = GridLayout.LayoutParams.MATCH_PARENT;
////            params.height=60;
//            TextView textView = new TextView(this);
//            textView.setTextColor(getResources().getColor(R.color.blue));
//            textView.setText(content);
//            textView.setGravity(Gravity.CENTER);
//            mGlClsTitle.addView(textView,params);
//        }
//    }
//
//    //初始化课表显示的格子
//    private void setUpClsContent(){
//        //设置每行第几节课的提示
//        for(int i=0; i<GRID_ROW_COUNT+1; ++i){
//            int row = i;
//            int col = 0;
//            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
//                    GridLayout.spec(row),GridLayout.spec(col)
//            );
//            params.width = mTableDistance;
//            if (i == 0){
//                params.height = 0;
//            }
//            else {
//                params.height = (int) getResources().getDimension(R.dimen.table_row_height);
//            }
//            TextView textView = new TextView(this);
//            textView.setTextColor(getResources().getColor(R.color.blue));
//            textView.setText(i+"");
//            textView.setGravity(Gravity.CENTER);
//            textView.setBackground(getResources().getDrawable(R.drawable.table_frame));
//            mGlClsContent.addView(textView,params);
//        }
//        //初始化表格的距离
//        for (int i=1; i<GRID_COL_COUNT; ++i){
//            int row = 0;
//            int col = i;
//            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
//                    GridLayout.spec(row),GridLayout.spec(col)
//            );
//            params.width = mTableDistance*2;
//            params.height = 0;
//
//            View view = new View(this);
//            mGlClsContent.addView(view,params);
//        }
//    }

//    private void showCls(){
//        for (int i = 0; i< mStuCourseList.size(); ++i){
//            Course course = mStuCourseList.get(i);
//            int row = course.getClsNum();
//            int col = course.getDay();
//            int size = course.getClsCount();
//            //设定View在表格的哪行那列
//            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
//                    GridLayout.spec(row,size),
//                    GridLayout.spec(col)
//            );
//            //设置View的宽高
//            params.width = mTableDistance*2;
//            params.height = (int) getResources().getDimension(R.dimen.table_row_height) * size;
//            params.setGravity(Gravity.FILL);
//            //通过代码改变<Shape>的背景颜色
//            GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cls_bg);
//            drawable.setColor(getResources().getColor(course.getColor()));
//            //设置View
//            TextView textView = new TextView(this);
//            textView.setTextColor(getResources().getColor(R.color.white));
//            textView.setText(course.getClsName());
//            textView.setGravity(Gravity.CENTER);
//            textView.setBackground(drawable);
//            //添加到表格中
//            mGlClsContent.addView(textView,params);
//        }
//    }

    @Override
    protected void initClick() {

    }

    @Override
    protected void processLogin(Bundle savedInstanceState) {
        //首先从数据库获取值
        List<Classs> courses = mStuCourseDao.getStuClsList();
        mStuCourseList.addAll(courses);
        initData();
        initRv();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_get_course:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, REQUEST_LOGIN);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_LOGIN:
                    try {
                        //等待提示
                        final ProgressDialog dialog = new ProgressDialog(this);
                        dialog.setTitle("加载课程中");
                        dialog.show();
                        //加载数据
                        mCourseService.getCourse(SharePreferenceUntil.loadDataFromFile(SharePreferenceUntil.KEY_USERNAME),
                                new HttpConnection.HttpCallBack<List<Classs>>() {
                                    @Override
                                    public void callback(List<Classs> data) {
                                        //清空原有数据
                                        mStuCourseList.clear();
                                        mStuCourseDao.removeAll();
                                        mStuCourseList.addAll(data);
                                        mStuCourseDao.saveStuClsList(mStuCourseList);
                                        dialog.dismiss();
                                        initData();
                                        initRv();
                                    }
                                });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStuCourseDao.saveStuClsList(mStuCourseList);
    }

}
