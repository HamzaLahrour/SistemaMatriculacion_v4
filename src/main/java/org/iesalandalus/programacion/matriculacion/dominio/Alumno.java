package org.iesalandalus.programacion.matriculacion.dominio;

import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.IllegalFormatCodePointException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Alumno {

    private String ER_TELEFONO="[0-9]{9}";
    private String ER_CORREO="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,5}$";
    private String ER_DNI="([0-9]{8})([a-zA-Z])";
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

        retornoNombreFormateado=retornoNombreFormateado.trim();

        return retornoNombreFormateado;
    }


    public boolean comprobarLetraDni(String dni){
        String mat=dni;

        String patronDNI = "([0-9]{8})([a-zA-Z])";

        if (!dni.matches(patronDNI)){
            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        }
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

        boolean esValido=true;

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
                if (CaracterDNI!='H'){
                    esValido=false;
                    System.out.println("La letra del Dni no es valida");
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

       if (nombre==null){
           throw new NullPointerException("ERROR: El nombre de un alumno no puede ser nulo.");
       }

       if (nombre.isEmpty()){

           throw new IllegalArgumentException("ERROR: El nombre de un alumno no puede estar vacío.");
       }

        this.nombre = formateaNombre(nombre);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {

        if (telefono==null){
            throw new NullPointerException("ERROR: El teléfono de un alumno no puede ser nulo.");
        }

        if (telefono.isBlank()){
            throw new IllegalArgumentException("ERROR: El teléfono del alumno no tiene un formato válido.");
        }

        if (!telefono.matches(ER_TELEFONO)){
            throw new IllegalArgumentException("ERROR: El teléfono del alumno no tiene un formato válido.");
        }

        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {

        if (correo==null){
            throw new NullPointerException("ERROR: El correo de un alumno no puede ser nulo.");
        } else if (correo.isBlank()) {
            throw new IllegalArgumentException("ERROR: El correo del alumno no tiene un formato válido.");
        }

        if (!Pattern.matches(ER_CORREO, correo)) {
            throw new IllegalArgumentException("ERROR: El correo del alumno no tiene un formato válido.");
        }


        this.correo = correo;
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {





        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {


        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {

        DateTimeFormatter formatoDeLaFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA);
       fechaNacimiento.format(formatoDeLaFecha);

        LocalDate fechaDeHoy = LocalDate.now();
        fechaDeHoy.format(formatoDeLaFecha);

        long numeroAnios = ChronoUnit.YEARS.between(fechaNacimiento,fechaDeHoy);



        if (numeroAnios<MIN_EDAD_ALUMNADO){
            throw new IllegalArgumentException("ERROR: La edad del alumno debe ser mayor o igual a 16 años.");
        }
        this.fechaNacimiento = fechaNacimiento;


    }

    public String getNia() {



        return nia;
    }

    private void setNia(String nia) {

        this.nia = nia;
    }

    private void setNia (){

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

        tresDigitosDni=dni.substring(5,8);

        sb3.append(digitosNombreNia);
        sb3.append(tresDigitosDni);

        nia= sb3.toString();



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



        if (nombre==null){

            throw new NullPointerException("ERROR: El nombre de un alumno no puede ser nulo.");
        } else if (nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR: El nombre de un alumno no puede estar vacío.");
        }

        if (dni==null){
            throw new NullPointerException("ERROR: El dni de un alumno no puede ser nulo.");
        } else if (dni.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        }

        if (dni.length()!=9){
            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        }

        if (!comprobarLetraDni(dni)){
            throw new IllegalArgumentException("ERROR: La letra del dni del alumno no es correcta.");
        }

        if (!dni.matches(ER_DNI)){

            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        }

        if (fechaNacimiento==null){
            throw new NullPointerException("ERROR: La fecha de nacimiento de un alumno no puede ser nula.");
        }


        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);




        this.nombre = formateaNombre(nombre);
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;



        setNia();
    }

    public Alumno(Alumno alumno){

        if (alumno==null){

            throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
        }


        this.nombre=alumno.nombre;
        this.dni=alumno.dni;
        this.correo=alumno.correo;
        this.telefono=alumno.telefono;
        this.fechaNacimiento=alumno.fechaNacimiento;
        this.nia=alumno.nia;



    }

    public String imprimir (){
        return "nombre=" + getNombre() + ", " + "DNI=" + getDni();
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
        DateTimeFormatter formatoDeLaFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA);



        return "Número de Identificación del Alumnado (NIA)="+ nia + " nombre="+ nombre + " " + "("+getIniciales() +")" + ", " + "DNI=" + dni + "," + " correo=" + correo + "," + " teléfono=" + telefono + "," + " fecha nacimiento=" + fechaNacimiento.format(formatoDeLaFecha);
    }
}


