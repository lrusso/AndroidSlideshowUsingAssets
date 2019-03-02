package ar.com.dfactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SimpleViewPagerAdapter extends PagerAdapter
	{
	private Context mContext;
	private List<String> myimages = new ArrayList<String>();

	public SimpleViewPagerAdapter(Context mContext, int screenWidth, int screenHeight)
		{
		try
			{
			String[] f = mContext.getAssets().list("");
			for(String f1 : f)
				{
				if (f1.endsWith(".png") | f1.endsWith(".PNG"))
					{
					myimages.add(f1);
					}
				}
			Collections.sort(myimages, new Comparator<String>(){public int compare(String s1, String s2){return s1.compareToIgnoreCase(s2);}});
			}
			catch(Exception e)
			{
			}
		this.mContext = mContext;
		}

	@Override public int getCount()
		{
		return myimages.size();
		}

	@Override public boolean isViewFromObject(View view, Object object)
		{
		return view == ((RelativeLayout) object);
		}
	
	@Override public Object instantiateItem(ViewGroup container, int position)
		{
		ImageView imgflag;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.row_image_gallery, container, false);
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		imgflag = (ImageView) itemView.findViewById(R.id.hotspotImage);
		
		String assetFile = myimages.get(position);
		
		try
			{
			InputStream ims = mContext.getAssets().open(assetFile);
			Drawable d = Drawable.createFromStream(ims, null);
			imgflag.setImageDrawable(d);
			ims.close();
			}
			catch(Exception e) 
			{
			}
		
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imgflag.getLayoutParams();
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
		imgflag.setLayoutParams(layoutParams);
		((ViewPager) container).addView(itemView);
		return itemView;
		}

	@Override public void destroyItem(ViewGroup container, int position, Object object)
		{
		((ViewPager) container).removeView((RelativeLayout) object);
		}

	@Override public Parcelable saveState()
		{
		return null;
		}
	}