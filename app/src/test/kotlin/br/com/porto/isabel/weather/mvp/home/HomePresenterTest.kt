package br.com.porto.isabel.weather.mvp.home

import br.com.porto.isabel.weather.model.app.ForecastInterface
import br.com.porto.isabel.weather.model.app.WeatherData
import br.com.porto.isabel.weather.model.openweather.Current
import br.com.porto.isabel.weather.model.openweather.Forecast
import com.twistedequations.mvl.rx.TestAndroidRxSchedulers
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import rx.Observable

/**
 * Created by isabelporto on 20/10/2016.
 */

@RunWith(JUnitPlatform::class)
class HomePresenterTest : Spek({

    describe("a HomePresenter with mock views") {

        val model = mock(HomeContract.ModelContract::class.java)
        val view = mock(HomeContract.ViewContract::class.java)
        val presenter = HomePresenter(view, model, TestAndroidRxSchedulers());

        beforeEach {
            `when`(view.observeSelectCity()).thenReturn(Observable.never())
            `when`(view.observePullToRefresh()).thenReturn(Observable.never())
            `when`(view.observeTryAgainClick()).thenReturn(Observable.never())
            `when`(view.observeListItemClicks()).thenReturn(Observable.never())
        }

        group("when the a presenter onCreate is called") {

            group("and model has no saved data") {

                val weatherData = WeatherData(Current(), Forecast());

                beforeEach {
                    `when`(model.requestData()).thenReturn(Observable.just(weatherData))
                    presenter.onCreate()
                }

                it("should set the view to the loading state") {
                    verify(view, atLeastOnce()).showProgress();
                }

                it("should load the data into the view") {
                    verify(view, atLeastOnce()).showCurrentData(eq(weatherData.current));
                    verify(view, atLeastOnce()).showForecast(eq(weatherData.forecast));
                }

                it("should set the view to the non loading state") {
                    verify(view, atLeastOnce()).showContent()
                    verify(view, atLeastOnce()).hideSwipe()
                }
            }

        }
    }

})
