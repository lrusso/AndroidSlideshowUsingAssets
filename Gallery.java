package ar.com.dfactory;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

public class Gallery extends Activity
	{
	private ViewPager mPager;
	private SimpleViewPagerIndicator pageIndicator;
	private TextView description;

	@Override protected void onCreate(Bundle savedInstanceState)
		{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		description = (TextView) findViewById(R.id.description);
		initView();
		}

	@Override public boolean onCreateOptionsMenu(Menu menu)
		{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
		}

	public void initView()
		{
		mPager = (ViewPager) findViewById(R.id.pager);
		pageIndicator = (SimpleViewPagerIndicator) findViewById(R.id.page_indicator);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		SimpleViewPagerAdapter mPagerAdapter = new SimpleViewPagerAdapter(this, width, height);
		mPager.setOffscreenPageLimit(1);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(0);
		pageIndicator.setDescriptionTextView(description);
		pageIndicator.setViewPager(mPager);
		pageIndicator.notifyDataSetChanged();
		}
	}