    package tool.oyyw.opengl_test.work;

    import android.support.v4.view.PagerAdapter;
    import android.support.v4.view.ViewPager;
    import android.view.View;

    import java.util.ArrayList;

    /**
     * Created by Andrew on 2016/6/30.
     */


    public class MYViewPagerAdapter extends PagerAdapter {
        private ArrayList<View> views;

        public void setViews(ArrayList<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {

            ((ViewPager)container).removeView(views.get(position));
        }
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(views.get(position));
            return views.get(position);
        }

    }
