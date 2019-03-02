package ar.com.dfactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleViewPagerIndicator extends LinearLayout implements OnPageChangeListener
	{
	private Context context;
	private ViewPager pager;
	private OnPageChangeListener onPageChangeListener;
	private LinearLayout itemContainer;
	private List<ImageView> items;
	private List<String> myimages = new ArrayList<String>();
	private TextView description;

	public SimpleViewPagerIndicator(Context context)
		{
		super(context);
		this.context = context;
		loadAssets();
		setup();
		}

	public SimpleViewPagerIndicator(Context context, AttributeSet attrs)
		{
		super(context, attrs);
		this.context = context;
		loadAssets();
		setup();
		}

	public SimpleViewPagerIndicator(Context context, AttributeSet attrs, int defStyle)
		{
		super(context, attrs, defStyle);
		this.context = context;
		loadAssets();
		setup();
		}

	private void loadAssets()
		{
		myimages.clear();
		try
			{
			String[] f = context.getAssets().list("");
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
		}

	private void loadDescription(int position)
		{
		try
			{
			String valor = myimages.get(position);
			if (valor.contains("-"))
				{
				valor = valor.substring(valor.indexOf("-") + 1);
				valor = valor.substring(0,valor.length()-4);
				valor = valor.trim();
				description.setText(valor);
				}
				else
				{
				description.setText("");
				}
			}
			catch(Exception e)
			{
			description.setText("");
			}
		}

	private void setup() 
		{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (inflater != null)
			{
			inflater.inflate(R.layout.view_pager_indicator, this);
			itemContainer = (LinearLayout) findViewById(R.id.pager_indicator_container);
			items = new ArrayList<ImageView>();
			}
		}

	public void setDescriptionTextView(TextView a)
		{
		description = a;
		}

	public void notifyDataSetChanged()
		{
		if (pager != null && pager.getAdapter() != null)
			{
			itemContainer.removeAllViews();
			items.removeAll(items);

			for (int i = 0; i < pager.getAdapter().getCount(); i++) 
				{
				ImageView item = new ImageView(context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
																				LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(5, 5, 5, 5);
				item.setLayoutParams(lp);
				if (i == pager.getCurrentItem()) 
					{
					item.setImageResource(R.drawable.un_selected_dot);
					} 
					else 
					{
					item.setImageResource(R.drawable.selected_dot);
					loadDescription(pager.getCurrentItem());
					}

				item.setTag(i);
				items.add(item);

				itemContainer.addView(item);
				}
			}
		}

	public ViewPager getViewPager()
		{
		return pager;
		}

	public void setViewPager(ViewPager pager)
		{
		this.pager = pager;
		this.pager.setOnPageChangeListener(this);
		}

	public OnPageChangeListener getOnPageChangeListener()
		{
		return onPageChangeListener;
		}

	public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener)
		{
		this.onPageChangeListener = onPageChangeListener;
		}

	private void setCurrentItem(int position) 
		{
		try 
			{
			if (pager != null && pager.getAdapter() != null) 
				{
				int numberOfItems = pager.getAdapter().getCount();
				for (int i = 0; i < numberOfItems; i++) 
					{
					ImageView item = items.get(i);
					if (item != null) 
						{
						if (i == position) 
							{
							item.setImageResource(R.drawable.un_selected_dot);
							} 
							else 
							{
							item.setImageResource(R.drawable.selected_dot);
							loadDescription(position);
							}
						}
					}
				}
			}
			catch (Exception e)
			{
			}
		}

	@Override public void onPageScrollStateChanged(int state)
		{
		if (this.onPageChangeListener != null)
			{
			this.onPageChangeListener.onPageScrollStateChanged(state);
			}
		}

	@Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
		{
		if (this.onPageChangeListener != null)
			{
			this.onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		}

	@Override public void onPageSelected(int position)
		{
		setCurrentItem(position);
		if (this.onPageChangeListener != null)
			{
			this.onPageChangeListener.onPageSelected(position);
			}
		}
	}