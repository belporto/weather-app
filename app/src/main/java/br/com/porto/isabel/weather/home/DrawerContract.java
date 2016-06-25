package br.com.porto.isabel.weather.home;


public interface DrawerContract {

    interface FragmentContract {
        void onRefresh();
    }

    interface ActivityContract {
        void hideSwipe();
    }


}
