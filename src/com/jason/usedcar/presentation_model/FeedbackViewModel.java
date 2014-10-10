package com.jason.usedcar.presentation_model;

import com.jason.usedcar.RestClient;
import com.jason.usedcar.model.FeedbackModel;
import com.jason.usedcar.request.SuggestionRequest;
import com.jason.usedcar.response.Response;
import org.robobinding.aspects.PresentationModel;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-09-18.
 */
@PresentationModel
public class FeedbackViewModel {

    private ViewFeedback feedbackView;

    private FeedbackModel feedbackModel;

    public FeedbackViewModel(ViewFeedback feedbackView) {
        this.feedbackView = feedbackView;
        this.feedbackModel = new FeedbackModel();
    }

    public void feedback() {
        if (!feedbackView.isLogin()) {
            feedbackView.login();
            return;
        }
        feedbackView.feedbackBegin();
        SuggestionRequest suggestionRequest = new SuggestionRequest();
        suggestionRequest.setAccessToken(feedbackView.getAccessToken());
        suggestionRequest.setMessage(feedbackModel.getContent());
        new RestClient().suggestion(suggestionRequest, new Callback<Response>() {
            @Override
            public void success(final Response response, final retrofit.client.Response response2) {
                boolean success = (response != null) && response.isExecutionResult();
                feedbackView.feedbackEnd(success);
            }

            @Override
            public void failure(final RetrofitError error) {
                feedbackView.feedbackEnd(false);
            }
        });
    }

    public String getContent() {
        return feedbackModel.getContent();
    }

    public void setContent(String content) {
        feedbackModel.setContent(content);
    }

    public String getContactPhone() {
        return feedbackModel.getContactPhone();
    }

    public void setContactPhone(String contactPhone) {
        feedbackModel.setContactPhone(contactPhone);
    }
}
