package com.example.juansevillano.testingproductos;

/**
 * Created by Juan Sevillano on 25/04/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FragmentViewPagerAdapter2 {
    private List<Fragment> fragments; // Una página correspondiente a cada Fragment
    private FragmentManager fragmentManager;
    private ViewPager viewPager; // ViewPager objetos
    private int currentPageIndex = 0; // La página de índice actual (antes de la entrega)
    private OnExtraPageChangeListener onExtraPageChangeListener; // Cuando la función adicional de ViewPager cambiar las páginas añadir interfaz

    /**
     * Constructor
     *
     * @param fragmentManager
     * @param viewPager
     * @param fragments
     */
    public FragmentViewPagerAdapter2(FragmentManager fragmentManager,
                                     ViewPager viewPager, List<Fragment> fragments) {
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.viewPager = viewPager;
        // PagerAdapter
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        this.viewPager.setAdapter(myPagerAdapter);
        // PageChangeListener
        MyPageChangeListener myPageChangeListener = new MyPageChangeListener();
        this.viewPager.setOnPageChangeListener(myPageChangeListener);
    }

    /**
     * La página de índice actual (antes de la entrega)
     *
     * @return
     */
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    /**
     * PagerAdapter para utilizar PagerAdapter, en primer lugar, a la herencia PagerAdapter clase y luego cubrir al menos los siguientes métodos
     * instantiateItem(ViewGroup,
     * int)[De esta forma, un objeto de cambio, este objeto que PagerAdapter adaptador elegir qué objetos en la actual ViewPager]
     * destroyItem(ViewGroup, int, Object)[De esta forma, en vista de la actual del Grupo es]
     * getCount()[De esta forma, el número es obtener la forma actual de la interfaz] isViewFromObject(View, Object)
     * [De esta forma, en la documentación de la ayuda en el texto original es could be implemented as Return View ==
     * object,Es para determinar si el objeto de la generación de la interfaz]
     *
     * @author sansung
     *
     */
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = fragments.get(position);// Obtener la ubicación, la adquisición de Fragment.
            if (!fragment.isAdded()) { // Si no added fragmento
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(fragment, fragment.getClass().getSimpleName());
                ft.commit();
                /**
                 * En FragmentTransaction.commit (después de la presentación de FragmentTransaction) Método de objetos
                 * En el hilo principal del proceso, con el modo asíncrono para la ejecución.Si quieres la aplicación inmediata de la operación de esta esperando, voy a llamar a este método (llamada sólo en el hilo principal).
                 * Cuidado, todos - y los actos pertinentes en la ejecución de esta llamada es, por tanto, debe ser cuidadosamente la confirmación de la posición de llamadas de este método.
                 */
                fragmentManager.executePendingTransactions();
            }

            if (fragment.getView().getParent() == null) {
                container.addView(fragment.getView()); // El aumento en la distribución viewpager
            }

            return fragment.getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(fragments.get(position).getView()); // Fuera de viewpager a ambos lados del diseño de página
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    /**
     * OnPageChangeListener
     *
     * @author sansung
     *
     */
    class MyPageChangeListener implements OnPageChangeListener {
        /**
         * Este método es invocar en el momento de cambio de Estado, que tiene tres miembros arg0 este parámetro(0, 1, 2).  arg0
         * ==La hora está implícita la diapositiva diapositiva implícita de 1 hora, 2 arg0 = = terminó, implícita arg0 hora 0 = = no hizo nada.
         */
        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (null != onExtraPageChangeListener) { // Si la función de interfaz de configuración adicional
                onExtraPageChangeListener.onExtraPageScrollStateChanged(arg0);
            }
        }

        /**
         * Cuando la página en la diapositiva cuando se llama a este método, se detuvo en la diapositiva, antes de que este método ha sido llamado de vuelta.La implicación de los tres parámetros, respectivamente,:  arg0
         * :La página actual, pulse en la página y arg1 arg2 diapositiva: porcentaje de desviación de la posición de la página actual: la migración de píxeles de la página actual
         */
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (null != onExtraPageChangeListener) { // Si la función de interfaz de configuración adicional
                onExtraPageChangeListener.onExtraPageScrolled(arg0, arg1, arg2);
            }
        }

        /**
         * Este método es el salto de página después de recibir llamadas, arg0 es tu posición de la página seleccionada actualmente (número).
         */
        @Override
        public void onPageSelected(int arg0) {
            fragments.get(currentPageIndex).onPause(); // OnPause Fargment antes de cambiar la llamada()
            // fragments.get(currentPageIndex).onStop(); //
            // OnStop Fargment antes de cambiar la llamada()
            if (fragments.get(arg0).isAdded()) {
                // fragments.get(i).onStart(); // Onstart Fargment llamada después de la entrega()
                fragments.get(arg0).onResume(); // OnResume Fargment llamada después de la entrega()
            }

            currentPageIndex = arg0;

            if (null != onExtraPageChangeListener) { // Si la función de interfaz de configuración adicional
                onExtraPageChangeListener.onExtraPageSelected(arg0);
            }
        }
    }

    /**
     * Cambiar la función adicional de interfaz Page
     */
    static class OnExtraPageChangeListener {
        public void onExtraPageScrolled(int i, float v, int i2) {
        }

        public void onExtraPageSelected(int i) {
        }

        public void onExtraPageScrollStateChanged(int i) {
        }
    }

    /**
     * La función adicional de obtener el oyente cambiar la página
     *
     * @return onExtraPageChangeListener
     */
    public OnExtraPageChangeListener getOnExtraPageChangeListener() {
        return onExtraPageChangeListener;
    }

    /**
     * Preferencias cambiar la página de la función adicional de oyente
     *
     * @param onExtraPageChangeListener
     */
    public void setOnExtraPageChangeListener(
            OnExtraPageChangeListener onExtraPageChangeListener) {
        this.onExtraPageChangeListener = onExtraPageChangeListener;
    }

}