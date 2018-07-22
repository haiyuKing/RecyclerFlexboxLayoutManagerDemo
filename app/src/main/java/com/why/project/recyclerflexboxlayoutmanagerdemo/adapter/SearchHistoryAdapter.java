package com.why.project.recyclerflexboxlayoutmanagerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.why.project.recyclerflexboxlayoutmanagerdemo.R;
import com.why.project.recyclerflexboxlayoutmanagerdemo.bean.SearchHistoryBean;

import java.util.ArrayList;

/**
 * Created by HaiyuKing
 * Used 搜索记录列表适配器
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
	/**上下文*/
	private Context myContext;
	/**频道集合*/
	private ArrayList<SearchHistoryBean> listitemList;

	/**
	 * 构造函数
	 */
	public SearchHistoryAdapter(Context context, ArrayList<SearchHistoryBean> itemlist) {
		myContext = context;
		listitemList = itemlist;
	}

	/**
	 * 获取总的条目数
	 */
	@Override
	public int getItemCount() {
		return listitemList.size();
	}

	/**
	 * 创建ViewHolder
	 */
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(myContext).inflate(R.layout.searchhistory_list_item, parent, false);
		ItemViewHolder itemViewHolder = new ItemViewHolder(view);
		return itemViewHolder;
	}

	/**
	 * 声明grid列表项ViewHolder*/
	static class ItemViewHolder extends RecyclerView.ViewHolder
	{
		public ItemViewHolder(View view)
		{
			super(view);

			listItemLayout = (LinearLayout) view.findViewById(R.id.listitem_layout);
			mTitle = (TextView) view.findViewById(R.id.tv_title);
		}

		LinearLayout listItemLayout;
		TextView mTitle;
	}

	/**
	 * 将数据绑定至ViewHolder
	 */
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int index) {

		//判断属于列表项还是上拉加载区域
		if(viewHolder instanceof ItemViewHolder){
			SearchHistoryBean searchHistoryBean = listitemList.get(index);
			final ItemViewHolder itemViewHold = ((ItemViewHolder)viewHolder);

			itemViewHold.mTitle.setText(searchHistoryBean.getSearchTitle());

			//这里用不到
			/*ViewGroup.LayoutParams lp = itemViewHold.listItemLayout.getLayoutParams();
			if (lp instanceof FlexboxLayoutManager.LayoutParams) {
				FlexboxLayoutManager.LayoutParams flexboxLp =
						(FlexboxLayoutManager.LayoutParams) itemViewHold.listItemLayout.getLayoutParams();
				flexboxLp.setFlexGrow(1.0f);
				flexboxLp.setAlignSelf(AlignSelf.FLEX_END);
			}*/

			//如果设置了回调，则设置点击事件
			if (mOnItemClickLitener != null)
			{
				itemViewHold.listItemLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						int position = itemViewHold.getLayoutPosition();//在增加数据或者减少数据时候，position和index就不一样了
						mOnItemClickLitener.onItemClick(itemViewHold.listItemLayout, position);
					}
				});
			}

		}
	}

	/**
	 * 添加Item--用于动画的展现*/
	public void addItem(int position,SearchHistoryBean listitemBean) {
		listitemList.add(position,listitemBean);
		notifyItemInserted(position);
	}
	/**
	 * 删除Item--用于动画的展现*/
	public void removeItem(int position) {
		listitemList.remove(position);
		notifyItemRemoved(position);
	}

	/*=====================添加OnItemClickListener回调================================*/
	public interface OnItemClickLitener
	{
		void onItemClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
	{
		this.mOnItemClickLitener = mOnItemClickLitener;
	}
}