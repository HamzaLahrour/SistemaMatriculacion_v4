package org.iesalandalus.programacion.matriculacion.dominio;

import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Alumno {

    private String ER_TELEFONO;
    private String ER_CORREO;
    private String ER_DNI;
    public static final String FORMATO_FECHA = "dd/MM/YYYY";
    private String ER_NIA;
    private int MIN_EDAD_ALUMNADO=16;
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;
    private String nia;


    public String formateaNombre(String nombre){



        String[] palabras = nombre.split("\\s");
        StringBuilder stbld = new StringBuilder();
        String retornoNombreFormateado;

        for (String palabra : palabras) {



            if (!palabra.isEmpty()) {


                String capitalizada = palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();

                stbld.append(" " +capitalizada);
            }










        }

        retornoNombreFormateado=stbld.toString();

        return retornoNombreFormateado;
    }


    public boolean comprobarLetraDni(String dni){


        String mat="";


        String patronDNI = "([0-9]{8})([a-zA-Z])";

        Pattern pattern = Pattern.compile(patronDNI);
        Matcher matcher = pattern.matcher(mat);

        if (matcher.find()) {

            matcher.group(1);
            matcher.group(2);

        }


        int numeroDNI= Integer.parseInt(matcher.group(1));



        int almacenNumDNI=numeroDNI %23;



        String letraDNI=matcher.group(2);



        char CaracterDNI=letraDNI.charAt(0);



        CaracterDNI= Character.toUpperCase(CaracterDNI);

        boolean esValido=false;

        switch (almacenNumDNI){



            case 0:
                if (CaracterDNI!='T'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valAAAida A");

                }
                break;
            case 1:
                if (CaracterDNI!='R'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida B");

                }
                break;
            case 2:
                if (CaracterDNI!='W'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida C");

                }
                break;
            case 3:
                if (CaracterDNI!='A'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 4:
                if (CaracterDNI!='G'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 5:
                if (CaracterDNI!='M'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 6:
                if (CaracterDNI!='Y'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;

            case 7:
                if (CaracterDNI!='F'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 8:
                if (CaracterDNI!='P'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }

                break;
            case 9:
                if (CaracterDNI!='D'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 10:
                if (CaracterDNI!='X'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 11:
                if (CaracterDNI!='B'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }

                break;
            case 12:
                if (CaracterDNI!='N'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 13:
                if (CaracterDNI!='J'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }

                break;
            case 14:
                if (CaracterDNI!='Z'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 15:
                if (CaracterDNI!='S'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 16:
                if (CaracterDNI!='Q'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 17:
                if (CaracterDNI!='V'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 18:
                if (CaracterDNI=='H'){
                    esValido=false;

                    System.out.println("La letra del Dni es valida");

                }
                break;
            case 19:
                if (CaracterDNI!='L'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 20:
                if (CaracterDNI!='C'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 21:
                if (CaracterDNI!='K'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;
            case 22:
                if (CaracterDNI!='E'){
                    esValido=false;

                    System.out.println("La letra del Dni no es valida");

                }
                break;

        }







        return esValido;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {


        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) throws OperationNotSupportedException {

        DateTimeFormatter formatoDeLaFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA);
        fechaNacimiento.format(formatoDeLaFecha);

        LocalDate fechaDeHoy = LocalDate.now();
        fechaDeHoy.format(formatoDeLaFecha);

        long numeroAnios = ChronoUnit.YEARS.between(fechaNacimiento,fechaDeHoy);

        if (numeroAnios<MIN_EDAD_ALUMNADO){

            throw new OperationNotSupportedException("ERROR:eL KONDF");


        }


        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNia() {
        return nia;
    }

    public void setNia() {



        if (nombre == null || dni == null || nombre.isEmpty() || dni.isEmpty()) {
            System.out.println("Nombre o DNI no pueden ser nulos o vacíos.");
            return;
        }


        String[] palabras = nombre.split("\\s");
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();

        String almacenNombre;

        for (String palabra : palabras) {

            sb2.append(palabra);


        }


        almacenNombre=sb2.toString();


        String digitosNombreNia;

        digitosNombreNia=almacenNombre.substring(0,4).toLowerCase().trim();





        String tresDigitosDni;

        tresDigitosDni=dni.substring(6,9);

        sb3.append(digitosNombreNia);
        sb3.append(tresDigitosDni);

        nia= sb3.toString();



        this.nia = nia;
    }

    public String getIniciales (){

        String[] palabras = nombre.split("\\s");

        String c = "";
        for (int i = 0; i < palabras.length; i++) {
            if (!palabras[i].equals(""))
                c = c + palabras[i].charAt(0);
        }


        String inciales;

        inciales=c.toUpperCase();

        return inciales;
    }

    public Alumno(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Alumno(Alumno alumno){



    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return Objects.equals(dni, alumno.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }


    @Override
    public String toString() {
        return "Número de Identificación del Alumnado (NIA)="+ nia + " nombre="+ nombre + " " + getIniciales() + ", " + "DNI=" + dni + "," + " correo=" + correo + "," + " teléfono=" + telefono + "," + " fecha nacimiento=" + fechaNacimiento;
    }
}


