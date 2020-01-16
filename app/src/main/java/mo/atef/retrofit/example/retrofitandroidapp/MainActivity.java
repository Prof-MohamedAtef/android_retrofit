package mo.atef.retrofit.example.retrofitandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult=(TextView) findViewById(R.id.text_view_result);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi=retrofit.create(JsonPlaceHolderApi.class);

        getPosts();

//        getComments();
    }

    private void getComments() {
        Call<List<Comment>> call=jsonPlaceHolderApi.getComments(3);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("code: "+response.code());
                    return;
                }

                List<Comment> comments=response.body();

                for (Comment comment:comments){
                    String content="";
                    content+="ID: "+comment.getId()+"\n";
                    content+="post ID: "+comment.getPostId()+"\n";
                    content+="Name: "+comment.getName()+"\n";
                    content+="Email: "+comment.getEmail()+"\n";
                    content+="text: "+comment.getText()+"\n\n";

                    textViewResult.append(content );
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getPosts() {
        Call<List<Post>> call=jsonPlaceHolderApi.getPosts(4);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("code:" +response.code());
                    return;
                }

                List<Post> posts=response.body();

                for (Post post:posts){
                    String content="";
                    content+="ID: "+post.getId()+"\n";
                    content+="userID: "+post.getUserId()+"\n";
                    content+="title: "+post.getTitle()+"\n";
                    content+="text: "+post.getText()+"\n\n";

                    textViewResult.append(content );
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
