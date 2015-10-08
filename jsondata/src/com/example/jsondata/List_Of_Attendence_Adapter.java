package com.example.jsondata;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class List_Of_Attendence_Adapter extends ArrayAdapter<Attendence_Bean> {

	private Context context;
	private int resId;
	public ArrayList<Attendence_Bean> list_data;

	private ArrayList<Attendence_Bean> searchtList;
//	private HolderFilter holderFilter;

	ImageLoader imageLoader;
	
	public List_Of_Attendence_Adapter(Context context, int textViewResourceId,
			ArrayList<Attendence_Bean> data) {
		super(context, textViewResourceId, data);
		// TODO Auto-generated constructor stub

		this.context = context;
		this.resId = textViewResourceId;
		this.list_data = data;
		this.searchtList = data;
		imageLoader = new ImageLoader(context);
		
	}

	private class ViewHolder {
		TextView Firstname, LastName;
		ImageView Pimage;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {

			convertView = mInflater.inflate(resId, null);
			holder = new ViewHolder();
			holder.Firstname = (TextView) convertView
					.findViewById(R.id.firstname);
			holder.LastName = (TextView) convertView
					.findViewById(R.id.lastname);

			holder.Pimage = (ImageView) convertView
					.findViewById(R.id.Profile_image);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.Firstname.setText(list_data.get(position).getFirstName());
		holder.LastName.setText(list_data.get(position).getLastName());
		//holder.Firstname.setText(list_data.get(position).getFirstName());
		imageLoader.DisplayImage(list_data.get(position).getImage(), holder.Pimage);

		return convertView;

	}

//	@Override
//	public Filter getFilter() {
//		// TODO Auto-generated method stub
//		if (holderFilter == null) {
//			holderFilter = new HolderFilter();
//		}
//		return holderFilter;
//	}

	@Override
	public int getCount() {
		return list_data.size();
	}

//	private class HolderFilter extends Filter {
//		@SuppressLint("DefaultLocale")
//		@Override
//		protected FilterResults performFiltering(CharSequence constraint) {
//			FilterResults results = new FilterResults();
//			if (constraint == null || constraint.length() == 0) {
//				results.values = searchtList;
//				results.count = searchtList.size();
//			} else {
//				List<Attendence_Bean> nHolderList = new ArrayList<Attendence_Bean>();
//				nHolderList.clear();
//				for (Attendence_Bean h : searchtList) {
//					if (h.getCname().toUpperCase()
//							.contains(constraint.toString().toUpperCase())) {
//						nHolderList.add(h);
//					}
//				}
//				results.values = nHolderList;
//				results.count = nHolderList.size();
//			}
//			return results;
//		}
//
//		@SuppressWarnings("unchecked")
//		@Override
//		protected void publishResults(CharSequence constraint,
//				FilterResults results) {
//			if (results.count == 0) {
//				list_data = (ArrayList<Attendence_Bean>) results.values;
//				notifyDataSetChanged();
//
//			} else {
//
//				list_data = (ArrayList<Attendence_Bean>) results.values;
//				notifyDataSetChanged();
//			}
//
//		}
//	}

}
