package retrofit;

import retrofit.model.check_update_observer.request.CheckUpdateObserverRequestEnvelope;
import retrofit.model.check_update_observer.response.CheckUpdateObserverResponseEnvelope;
import retrofit.model.create_arrest.request.CreateArrestRequestEnvelope;
import retrofit.model.create_arrest.response.CreateArrestResponseEnvelope;
import retrofit.model.create_uncheck_arrest.request.CreateUncheckArrestRequestEnvelope;
import retrofit.model.create_uncheck_arrest.response.CreateUncheckArrestResponseEnvelope;
import retrofit.model.get_car_details.request.GetCarDetailsRequestEnvelope;
import retrofit.model.get_car_details.response.GetCarDetailsResponseEnvelope;
import retrofit.model.get_cars_on_evacuation.request.GetCarsOnEvacuationRequestEnvelope;
import retrofit.model.get_cars_on_evacuation.response.GetCarsOnEvacuationResponseEnvelope;
import retrofit.model.get_cars_on_parking.request.GetCarsOnParkingRequestEnvelope;
import retrofit.model.get_cars_on_parking.response.GetCarsOnParkingResponseEnvelope;
import retrofit.model.get_image_full.request.GetImageFullRequestEnvelope;
import retrofit.model.get_image_full.response.GetImageFullResponseEnvelope;
import retrofit.model.get_last_evacuated.request.GetLastEvacuatedRequestEnvelope;
import retrofit.model.get_last_evacuated.response.GetLastEvacuatedResponseEnvelope;
import retrofit.model.get_photos.request.GetPhotosRequestEnvelope;
import retrofit.model.get_photos.response.GetPhotosResponseEnvelope;
import retrofit.model.test_connection.request.TestRequestEnvelope;
import retrofit.model.test_connection.response.TestResponseEnvelope;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitService {

    @POST("ws/ObserverServices")
    Call<TestResponseEnvelope> executeTestOperation(@Header("Authorization") String auth, @Body TestRequestEnvelope request);
    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/ObserverServices")
    Call<CheckUpdateObserverResponseEnvelope> executeCheckUpdateObserver(@Header("Authorization") String auth, @Body CheckUpdateObserverRequestEnvelope request);

    @POST("ws/ObserverServices")
    Call<GetCarsOnParkingResponseEnvelope> executeGetCarOnParking(@Header("Authorization") String auth, @Body GetCarsOnParkingRequestEnvelope request);

    @POST("ws/ObserverServices")
    Call<GetCarsOnEvacuationResponseEnvelope> executeGetCarsOnEvacuation(@Header("Authorization") String auth, @Body GetCarsOnEvacuationRequestEnvelope request);


    @POST("ws/ObserverServices")
    Call<GetCarDetailsResponseEnvelope> executeGetCarDetails(@Header("Authorization") String auth, @Body GetCarDetailsRequestEnvelope request);

    @POST("ws/ObserverServices")
    Call<GetImageFullResponseEnvelope> executeGetImageFull(@Header("Authorization") String auth, @Body GetImageFullRequestEnvelope request);

    @POST("ws/ObserverServices")
    Call<CreateArrestResponseEnvelope> executeCreateArrest(@Header("Authorization") String auth, @Body CreateArrestRequestEnvelope request);

    @POST("ws/ObserverServices")
    Call<CreateUncheckArrestResponseEnvelope> executeCreateUncheckArrest(@Header("Authorization") String auth, @Body CreateUncheckArrestRequestEnvelope request);

    @POST("ws/ObserverServices")
    Call<GetPhotosResponseEnvelope> executeGetPhotos(@Header("Authorization") String auth, @Body GetPhotosRequestEnvelope request);

    @POST("ws/ObserverServices")
    Call<GetLastEvacuatedResponseEnvelope> executeGetLastEvacuated(@Header("Authorization") String auth, @Body GetLastEvacuatedRequestEnvelope request);

}
