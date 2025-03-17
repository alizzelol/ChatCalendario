package com.alizzelol.chatcalendario.modelchat;

import java.util.Date;

public class Mensaje {
    private String emisor;
    private String texto;
    private Date timestamp;

    public Mensaje() {}

    public Mensaje(String emisor, String texto, Date timestamp) {
        this.emisor = emisor;
        this.texto = texto;
        this.timestamp = timestamp;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

