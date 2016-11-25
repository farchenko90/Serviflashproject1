package org.fabio.serviflashproject.Services;

import android.os.StrictMode;

import org.fabio.serviflashproject.Modelos.Cliente;
import org.fabio.serviflashproject.Modelos.Pedido;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;



/**
 * Created by root on 19/05/16.
 */
public class WebServices {

    public String msgError;
    public String mensaje;
    /*
    * Inicio sesion de cliente
    */
    public JSONObject login(Cliente c)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"logincliente");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("user", c.getEmail());
            jsonParam.put("clave", c.getPass());
            System.out.println(jsonParam);
            //Log.i("PARAMETROS: ",jsonParam.toString());

            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            //printout.writeUTF(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            byte[] buf = jsonParam.toString().getBytes("UTF-8");
            printout.write(buf);
            printout.flush ();
            printout.close ();
            BufferedReader rd;
            String jsonText;
            JSONObject json;
            switch (connection.getResponseCode()){
                case 404:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;
                case 200:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    return null;
            }

        }catch(Exception ex)
        {
            msgError = "Error: "+ex.getMessage();
        }
        return null;
    }
    /*
    * Actualizar push
    */
    public JSONObject updatepush(String idpush, int id){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"push/"+id);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idpush", idpush);
            System.out.println(jsonParam);

            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            //printout.writeUTF(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            byte[] buf = jsonParam.toString().getBytes("UTF-8");
            printout.write(buf);
            printout.flush ();
            printout.close ();
            BufferedReader rd;
            String jsonText;
            JSONObject json;
            switch (connection.getResponseCode()){
                case 404:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;
                case 200:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    return null;
            }

        }catch (Exception ex){
            msgError = "Error: "+ex.getMessage();
        }
        return null;
    }

    public JSONObject loginfacebook(Cliente c)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"cliente/sesion/facebook");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("email", c.getEmail());
            jsonParam.put("nombreape", c.getNombreape());
            jsonParam.put("idface",c.getIdface());
            System.out.println(jsonParam);
            //Log.i("PARAMETROS: ",jsonParam.toString());

            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            //printout.writeUTF(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            byte[] buf = jsonParam.toString().getBytes("UTF-8");
            printout.write(buf);
            printout.flush ();
            printout.close ();
            BufferedReader rd;
            String jsonText;
            JSONObject json;
            switch (connection.getResponseCode()){
                case 404:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;
                case 200:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    return null;
            }

        }catch(Exception ex)
        {
            msgError = "Error: "+ex.getMessage();
        }
        return null;
    }

    public JSONObject Registrarcliente(Cliente c)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"cliente");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("cedula", c.getCedula());
            jsonParam.put("nombreape", c.getNombreape());
            jsonParam.put("email", c.getEmail());
            jsonParam.put("login", c.getLogin());
            jsonParam.put("clave", c.getPass());
            jsonParam.put("celular", c.getCelular());
            jsonParam.put("telefono", c.getTelefono());
            jsonParam.put("direccion", c.getDireccion());
            jsonParam.put("sexo", c.getSexo());
            System.out.println(jsonParam);
            //Log.i("PARAMETROS: ",jsonParam.toString());

            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            //printout.writeUTF(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            byte[] buf = jsonParam.toString().getBytes("UTF-8");
            printout.write(buf);
            printout.flush ();
            printout.close ();
            BufferedReader rd;
            String jsonText;
            JSONObject json;
            switch (connection.getResponseCode()){
                case 404:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;
                case 200:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    return null;
            }

        }catch(Exception ex)
        {
            msgError = "Error: "+ex.getMessage();
        }
        return null;
    }

    public JSONObject solicitarpedido(Pedido p)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"pedido/cliente");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("origenlat", p.getOrigenlat());
            jsonParam.put("origenlong", p.getOrigenlong());
            jsonParam.put("destinolat", p.getDestinolat());
            jsonParam.put("barriodestino", p.getBarrio());
            jsonParam.put("destinolong", p.getDestinolong());
            jsonParam.put("descripcion", p.getDescripcion());
            jsonParam.put("direcciondestino", p.getDirecciondestino());
            jsonParam.put("direccionorigen", p.getDireccionorigen());
            jsonParam.put("idcliente", p.getIdcliente());
            System.out.println(jsonParam);
            //Log.i("PARAMETROS: ",jsonParam.toString());

            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            //printout.writeUTF(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            byte[] buf = jsonParam.toString().getBytes("UTF-8");
            printout.write(buf);
            printout.flush ();
            printout.close ();
            BufferedReader rd;
            String jsonText;
            JSONObject json;
            switch (connection.getResponseCode()){
                case 404:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;
                case 200:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    return null;
            }

        }catch(Exception ex)
        {
            msgError = "Error: "+ex.getMessage();
        }
        return null;
    }

    public JSONObject miperfil(int id)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"cliente/"+id);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            switch (connection.getResponseCode()){
                case 404:
                    mensaje = "Datos no encontrados";
                    System.out.println(mensaje);
                    return null;
                case 200:
                    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    String jsonText = readAll(rd);
                    JSONObject json = new JSONObject(jsonText);
                    connection.disconnect();
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    return null;
            }

        }catch(Exception ex)
        {
            System.out.println("excepcion: "+ex);
            mensaje = null;
        }
        return null;
    }

    public JSONObject Modificarcliente(Cliente c, int id)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"cliente/"+id);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("cedula", c.getCedula());
            jsonParam.put("nombreape", c.getNombreape());
            jsonParam.put("email", c.getEmail());
            jsonParam.put("login", c.getLogin());
            //System.out.println(jsonParam);
            //Log.i("PARAMETROS: ",jsonParam.toString());

            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            //printout.writeUTF(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            byte[] buf = jsonParam.toString().getBytes("UTF-8");
            printout.write(buf);
            printout.flush ();
            printout.close ();
            BufferedReader rd;
            String jsonText;
            JSONObject json;
            switch (connection.getResponseCode()){
                case 404:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;
                case 200:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    return null;
            }

        }catch(Exception ex)
        {
            msgError = "Error: "+ex.getMessage();
        }
        return null;
    }

    public JSONObject Modificarpass(String anterior, String nueva, int id)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"cambiarpass/"+id);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("clave", anterior);
            jsonParam.put("clavenueva", nueva);
            //System.out.println(jsonParam);
            //Log.i("PARAMETROS: ",jsonParam.toString());

            DataOutputStream printout = new DataOutputStream(connection.getOutputStream());
            //printout.writeUTF(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            byte[] buf = jsonParam.toString().getBytes("UTF-8");
            printout.write(buf);
            printout.flush ();
            printout.close ();
            BufferedReader rd;
            String jsonText;
            JSONObject json;
            switch (connection.getResponseCode()){
                case 404:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;
                case 200:
                    rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    jsonText = readAll(rd);
                    json = new JSONObject(jsonText);

                    connection.disconnect();
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    return null;
            }

        }catch(Exception ex)
        {
            msgError = "Error: "+ex.getMessage();
        }
        return null;
    }

    public JSONArray listapedidos(int id)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"cliente/"+id+"/pedidos");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            switch (connection.getResponseCode()){
                case 404:
                    mensaje = "Datos no encontrados";
                   // System.out.println("DAtos: "+mensaje);
                    return null;
                case 200:
                    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    String jsonText = readAll(rd);
                    JSONArray json = new JSONArray(jsonText);
                    connection.disconnect();
                    //System.out.println("case 200");
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    //System.out.println("DAtos: "+mensaje);
                    return null;
            }

        }catch(Exception ex)
        {
            System.out.println("excepcion: "+ex);
            mensaje = null;
        }
        return null;
    }

    public JSONObject pedido(int id)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"pedido/"+id);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            switch (connection.getResponseCode()){
                case 404:
                    mensaje = "Datos no encontrados";
                    System.out.println(mensaje);
                    return null;
                case 200:
                    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    String jsonText = readAll(rd);
                    JSONObject json = new JSONObject(jsonText);
                    connection.disconnect();
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    return null;
            }

        }catch(Exception ex)
        {
            System.out.println("excepcion: "+ex);
            mensaje = null;
        }
        return null;
    }

    public JSONObject mensajero(int id)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(Server.ruta+"mensajero/"+id+"/ubicacion");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            switch (connection.getResponseCode()){
                case 404:
                    mensaje = "Datos no encontrados";
                    System.out.println(mensaje);
                    return null;
                case 200:
                    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
                    String jsonText = readAll(rd);
                    JSONObject json = new JSONObject(jsonText);
                    connection.disconnect();
                    return json;

                default:
                    mensaje = "Error interno en el servidor";
                    return null;
            }

        }catch(Exception ex)
        {
            System.out.println("excepcion: "+ex);
            mensaje = null;
        }
        return null;
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
