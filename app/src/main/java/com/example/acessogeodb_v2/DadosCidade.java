package com.example.acessogeodb_v2;

public class DadosCidade {
    private String id;
    private String cidade;
    private String estado;
    private String pais;
    private String cod_pais;
    private String elevacao;
    private String latitude;
    private String longitude;
    private String timezone;
    private int populacao;


    public DadosCidade(String id, String cidade, String estado, String pais, String cod_pais, String elevacao, String latitude, String longitude, String timezone, int populacao) {
        this.id = id;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cod_pais = cod_pais;
        this.elevacao = elevacao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
        this.populacao = populacao;

    }

    public DadosCidade() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCod_pais() {
        return cod_pais;
    }

    public void setCod_pais(String cod_pais) {
        this.cod_pais = cod_pais;
    }

    public String getElevacao() {
        return elevacao;
    }

    public void setElevacao(String elevacao) {
        this.elevacao = elevacao;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getPopulacao() {
        return populacao;
    }

    public void setPopulacao(int populacao) {
        this.populacao = populacao;
    }


}