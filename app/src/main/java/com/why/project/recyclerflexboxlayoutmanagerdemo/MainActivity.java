package com.why.project.recyclerflexboxlayoutmanagerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.why.project.recyclerflexboxlayoutmanagerdemo.adapter.SearchHistoryAdapter;
import com.why.project.recyclerflexboxlayoutmanagerdemo.bean.SearchHistoryBean;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

	private RecyclerView mRecyclerView;
	private ArrayList<SearchHistoryBean> mSearchHistoryBeanArrayList;
	private SearchHistoryAdapter mSearchHistoryAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		initDatas();
		initEvents();
	}

	private void initViews() {
		mRecyclerView = findViewById(R.id.recycler_view);
	}

	private void initDatas() {
		//初始化集合
		mSearchHistoryBeanArrayList = new ArrayList<SearchHistoryBean>();
		String[] testDatas = new String[]{"牙刷","灭蚊器","移动空调","吸尘器","布衣柜","收纳箱 书箱","暑期美食满99减15","挂烫机","吸水拖把","反季特惠"};
		for(int i=0; i<testDatas.length;i++){
			SearchHistoryBean channelBean = new SearchHistoryBean();
			channelBean.setSearchTitle(testDatas[i]);
			//获取当前日期
			Calendar calendar = Calendar.getInstance();
			channelBean.setSearchDate(calendar.getTime());

			mSearchHistoryBeanArrayList.add(channelBean);
		}

		//设置布局管理器
		FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(MainActivity.this);
		//flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
		flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
		//flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
		flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
		//justifyContent 属性定义了项目在主轴上的对齐方式。
		flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。

		mRecyclerView.setLayoutManager(flexboxLayoutManager);

		//设置适配器
		if(mSearchHistoryAdapter == null){
			//设置适配器
			mSearchHistoryAdapter = new SearchHistoryAdapter(this, mSearchHistoryBeanArrayList);
			mRecyclerView.setAdapter(mSearchHistoryAdapter);
			//添加分割线
			//设置添加删除动画
			//调用ListView的setSelected(!ListView.isSelected())方法，这样就能及时刷新布局
			mRecyclerView.setSelected(true);
		}else{
			mSearchHistoryAdapter.notifyDataSetChanged();
		}
	}private void initEvents() {
		//列表适配器的点击监听事件
		mSearchHistoryAdapter.setOnItemClickLitener(new SearchHistoryAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(MainActivity.this, mSearchHistoryBeanArrayList.get(position).getSearchTitle(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
