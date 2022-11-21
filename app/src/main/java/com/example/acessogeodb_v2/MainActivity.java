package com.example.acessogeodb_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// GitHub:
// https://github.com/udofritzke/AcessoGeoDB_v2

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mBotaoBuscaId;
    private Button mBotaoBuscaDados;
    private TextView mTextViewDados;
    private TextView mTextViewDados2;
    private EditText mEditText;
    Response response;
    Response response2;
    String cityId = null;
    Editable nomeCidade = null;

    private class TarefaBuscaCityId extends AsyncTask<Void, Void, String> {
        @Override
        public String doInBackground(Void... params) {
            DadosCidade dadosGeoDB = null;
            // chama endpoint para busca do "id" da cidade
            if (nomeCidade != null)
                cityId = getID(nomeCidade);
            return cityId;
        }

        @Override
        public void onPostExecute(String cityId) {
            Log.i(TAG, "onPostExecute: TarefaBuscaCityId executada");
            mTextViewDados = (TextView) findViewById(R.id.view_texto_dos_dados);
            mTextViewDados.setText(cityId);
        }
    }

    private class TarefaBuscaDadosCidades extends AsyncTask<Void, Void, DadosCidade> {
        @Override
        protected DadosCidade doInBackground(Void... params) {
            DadosCidade dadosGeoDB = null;
            if (cityId != null)
                dadosGeoDB = getDadosCidade(cityId);
            return dadosGeoDB;

        }

        public void onPostExecute(DadosCidade resultado ) {
            Log.i(TAG, "onPostExecute: TarefaBuscaDadosCidades executada");
            if (resultado != null) {
                Log.i(TAG, "onPostExecute: dados recebidos: " + resultado.getCidade());
                String texto = "Cidade: " + resultado.getCidade() + "\n" +
                        "Estado: " + resultado.getEstado() + "\n" +
                        "País: " + resultado.getPais() + "/" + resultado.getCod_pais() + "\n" +
                        "Latitude: " + resultado.getLatitude() + " latitude" + "\n" +
                        "Longitude: " + resultado.getLongitude() + " longitude" + "\n"+
                        "Elevação: " + resultado.getElevacao() + " m" + "\n" +
                        "Fuso horário: " + resultado.getTimezone() + " timezone" + "\n" +
                        "População: " + resultado.getPopulacao() + " habitantes" + "\n";


                mTextViewDados = (TextView) findViewById(R.id.view_texto_dos_dados);
                mTextViewDados.setText(texto);
            }
        }

    }

    private class TarefaBuscaDateTime extends AsyncTask<Void, Void, DateTime> {
        @Override

        protected DateTime doInBackground(Void... params) {
            DateTime dadosDateTime = null;
            if (cityId != null)
                dadosDateTime = getDT(cityId);
            return dadosDateTime;
        }

        public void onPostExecute(DateTime resultado2) {
            Log.i(TAG, "onPostExecute: TarefaBuscaDateTime executada");
            if (resultado2 != null) {
                Log.i(TAG, "onPostExecute: dados recebidos: " + resultado2.getDT());
                String texto2 = "Data e hora da pesquisa:" + "\n" + resultado2.getDT();

                mTextViewDados2 = (TextView) findViewById(R.id.view_texto_date_time);
                mTextViewDados2.setText(texto2);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextViewDados = findViewById(R.id.view_texto_dos_dados);
        mBotaoBuscaId = (Button) findViewById(R.id.botaoBuscaId);
        mBotaoBuscaId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText = (EditText) findViewById(R.id.textoNomeCidade);
                nomeCidade = mEditText.getText();
                AsyncTask<Void, Void, String> tar = new TarefaBuscaCityId();
                tar.execute();
            }
        });

        mBotaoBuscaDados = (Button) findViewById(R.id.botaoBuscaDados);
        mBotaoBuscaDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask<Void, Void, DadosCidade> tar = new TarefaBuscaDadosCidades();
                tar.execute();
                AsyncTask<Void, Void, DateTime> tar2 = new TarefaBuscaDateTime();
                tar2.execute();

            }


        });

    }

    String getID(Editable cidade) {
        String CityId = null;
        OkHttpClient client = new OkHttpClient();
        String url = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?namePrefix=" + cidade;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "bd3d37c2b2msh7ee3d3e117c320bp114392jsn3f6f9285726a")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String responseBody = response.body().string();

            // parse do item recebido
            JSONObject corpoJson = new JSONObject(responseBody);
            JSONArray dataJsonArray = corpoJson.getJSONArray("data");
            JSONObject dataJasonObject = dataJsonArray.getJSONObject(0);
            CityId = dataJasonObject.getString("id");
            Log.i(TAG, "doInBackground: " + responseBody);
            Log.i(TAG, "doInBackground/CityId: " + CityId);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return CityId;
    }

    DadosCidade getDadosCidade(String cityId) {
        DadosCidade dadosGeoDB = null;
        OkHttpClient client = new OkHttpClient();
        String url = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities/" + cityId;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "bd3d37c2b2msh7ee3d3e117c320bp114392jsn3f6f9285726a")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String responseBody = response.body().string();

            // parse do item recebido
            JSONObject corpoJson = new JSONObject(responseBody);
            JSONObject dataJsonObject = corpoJson.getJSONObject("data");
            dadosGeoDB = new DadosCidade(
                    dataJsonObject.getString("id"),
                    dataJsonObject.getString("name"),
                    dataJsonObject.getString("region"),
                    dataJsonObject.getString("country"),
                    dataJsonObject.getString("countryCode"),
                    dataJsonObject.getString("elevationMeters"),
                    dataJsonObject.getString("latitude"),
                    dataJsonObject.getString("longitude"),
                    dataJsonObject.getString("timezone"),
                    (int) new Integer(dataJsonObject.getString("population"))

            );
            Log.i(TAG, "doInBackground: " + responseBody);
            Log.i(TAG, "doInBackground/cidade: " + dataJsonObject.getString("name"));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return dadosGeoDB;
    }

    DateTime getDT(String cityId) {
        DateTime dadosDateTime = null;
        OkHttpClient client2 = new OkHttpClient();
        String url = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities/" + cityId + "/dateTime";

        Request request2 = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "bd3d37c2b2msh7ee3d3e117c320bp114392jsn3f6f9285726a")
                .build();

        Response response2 = null;
        try {
            response2 = client2.newCall(request2).execute();
            String responseBody2 = response2.body().string();

            // parse do item recebido
            Log.i(TAG, "onPostExecute: parse do item recebido");
            JSONObject corpoJson2 = new JSONObject(responseBody2);
            JSONObject dataJasonObject2 = corpoJson2;
            Log.i(TAG, "onPostExecute dataJasonObject2: " + dataJasonObject2);
            dadosDateTime = new DateTime(
                   dataJasonObject2.getString("data")
            );

            Log.i(TAG, "doInBackground: responseBody2: " + responseBody2);
           // Log.i(TAG, "doInBackground/cidade: " + dataJsonObject2.getString("name"));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return dadosDateTime;
    }

}