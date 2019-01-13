package AFNaAFD_automatas;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mauricio
 */
public class Controlador {
    /*Tabla de resultado de AFD*/
    DefaultTableModel tbm = (DefaultTableModel)Vista.jTable2.getModel();
    String[]ESTADOS_ALFABETO = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    String[] ESTADOS_EXTRA = {"x","y","z"};
    String q="estado ";
    String punt=""; 
    String resultado;
    String e="e";
    String estadoInicial;
    String estadosQ="";
    String alfabetoF="";
    String EstadoAceptacion="";
    String estadoAceptacionAFN;
    String aux="";
    String[] nodoActual;
    String[] nodoAnteriro;
    String[] nodos;
    String[] alfabeto;
    String[] fila;
    String[] W;   
    Integer numeroNodos;
    Integer nNod=0;
    Integer filatabla=0;
    Integer numeroAlfabeto;
    Integer nNAlfa=0;
    Integer n=0;
    Integer numeroFila=0;
    ArrayList<String> EstadosNuevos = new ArrayList<>();
    String validarCadenaTxt= Vista.jTextField2.getText();
    private String variableGlobal="";
    String misEstadosNativos, misEstadosNumeros;

    
    public String getMisEstadosNativos() {
        return misEstadosNativos;
    }

    public void setMisEstadosNativos(String misEstadosNativos) {
        this.misEstadosNativos = misEstadosNativos;
    }

    public String getMisEstadosNumeros() {
        return misEstadosNumeros;
    }

    public void setMisEstadosNumeros(String misEstadosNumeros) {
        this.misEstadosNumeros = misEstadosNumeros;
    }
    
    public String getEstadosNativos(String est){
        String estadoARetornar = est.replace("{","");
        estadoARetornar = estadoARetornar.replace("}","");
        estadoARetornar = estadoARetornar.replace(",","");
        return estadoARetornar;
    }
    
    public String getEstadosNumeros(String estN){
        String retornarNumeros="";
        char[] cero = {'0'};
        if (estN.charAt(0) == cero[0]){
         for (int i= 0; i< estN.length();i++){
            if (i > 9){
               retornarNumeros = retornarNumeros + ESTADOS_EXTRA[i-10];
            }else {
                retornarNumeros = retornarNumeros + Integer.toString(i);
            }
        }   
        }else {
            for (int i= 0; i< estN.length();i++){
            if (i > 9){
               retornarNumeros = retornarNumeros + ESTADOS_EXTRA[i-10];
            }else {
                retornarNumeros = retornarNumeros + Integer.toString(i+1);
            }
        }
        }
        
        return retornarNumeros;
    }
    
    public String getEstadosEnNumeros(String estNativos,String estNumeros,String transiciones){
        String retTransic=transiciones;
        for (int i = 0 ; i < estNativos.length(); i++){
            retTransic = retTransic.replace(estNativos.charAt(i), estNumeros.charAt(i));
        }
        return retTransic;
    }
    
    public ArrayList<String> convertirEstadosNativos(String estNativos,String estNumeros,ArrayList<String> transiciones){
        ArrayList<String> retTransic=transiciones;
        int counter =0;
        for(String cadaTransic: retTransic){
        for (int i = 0 ; i < estNativos.length(); i++){
              retTransic.set(counter, cadaTransic.replace(estNumeros.charAt(i), estNativos.charAt(i)));
        }
        counter++;
        }
        return retTransic;
    }
    
    public String getComposicionNativa(String unaComposicion, String estNumeros, String estNativos){
        String replace = unaComposicion;
        for (int i=0 ; i < estNativos.length(); i++){
            replace = replace.replace(estNumeros.charAt(i), estNativos.charAt(i));
        }
        return replace;
    }
    
    public String cambiarAUnCaracter(String textoEstados){
        String textoCambiado = textoEstados;
        textoCambiado=textoCambiado.replace("10", "x");
        textoCambiado=textoCambiado.replace("11", "y");
        textoCambiado=textoCambiado.replace("12", "z");
        return textoCambiado;
    }
    
    public void convertir_AFD(String limpiando_texto , String alfabeto, String estados){        
        /*Verificar si hay 10, 11, 12*/
        limpiando_texto = cambiarAUnCaracter(limpiando_texto);
        estados = cambiarAUnCaracter(estados);
        /*Copiamos estado de aceptacion*/
        estadoAceptacionAFN = Vista.jTextPane4.getText();
        String estadosNativo = getEstadosNativos(estados);
        setMisEstadosNativos(estadosNativo);
        String estadosEnNumeros = getEstadosNumeros(estadosNativo);
        setMisEstadosNumeros(estadosEnNumeros);
        limpiando_texto = getEstadosEnNumeros(estadosNativo, estadosEnNumeros, limpiando_texto);
        /*agregamos las letras del alfabeto*/
        this.alfabetoF=this.alfabetoF+alfabeto;
        /*La tabla del AFD*/
        tbm = (DefaultTableModel) Vista.jTable2.getModel();
        /*Eliminando parentesis*/
        limpiando_texto= limpiando_texto.replace("(", "");
        limpiando_texto= limpiando_texto.replace("),", "/");
        /*Eliminando corchetes*/
        limpiando_texto= limpiando_texto.replace("{", "");
        limpiando_texto= limpiando_texto.replace("}", "");
        limpiando_texto= limpiando_texto.replace(")", "");
        nodos = limpiando_texto.split("/"); //[0] : 1,a,2 > [1] : 3,b,1 ...
        numeroNodos = nodos.length;
        this.alfabeto = alfabeto.split(",");//ab
        this.W =limpiando_texto.split("/");
        numeroAlfabeto = this.alfabeto.length;
        agregarAlfabeto(this.alfabeto); 
        nuevosEstados(nodos);
        validacionQuintupla();
    }
    
    
    
    private void nuevosEstados(String[] W){
            String composicion = primerestado(W);
            crearcerradura(composicion, alfabeto);
            
            for(int i=1; i<EstadosNuevos.size(); i++){
                nNAlfa =0;
                crearcerradura(EstadosNuevos.get(i), alfabeto);
            }
            modificarEstadosALetras();
    }
    
    private String estados(String[] W, String cerradura) {

        if(cerradura.equals("")){
            return "";
        }
        aux=cerradura;
            for(int i=0; i<W.length; i++){
            String[]W_aux;
            W_aux=W[i].split(",");
            if(aux.contains(W_aux[0]+",")){
                if(W_aux[1].equals(e)){
                    if(!aux.contains(W_aux[2]+",")){
                        aux=aux+W_aux[2]+",";
                    }                    
                }
            }
            n+=1;
            if(n<2){
                estados(W,aux);
            }
            
        }   
        return  ordenarResultado(aux);
        
    }

    private String primerestado(String[] W) {
        if(nNod < numeroNodos){ // Si 0 es menor a el numero de composiciones (1,a,2)... que tenemos.
            nodoActual = W[nNod].split(",");// de 1,a,2 >> 1a2
            if(nodoActual[1].equals(e)){//Si es igual a lambda
                if(resultado == null){
                    resultado = nodoActual[0]+","+nodoActual[2];
                    nodoAnteriro = nodoActual;
                    nNod+=1;            
                    primerestado(W);
                }else{
                    if(resultado.contains(nodoActual[0])){

                        if(nodoActual[1].equals(e)){

                            if(!resultado.contains(nodoActual[2])){                                
                                resultado = resultado+","+nodoActual[2];

                            }
                        }
                    }
                }
                nodoAnteriro = nodoActual;
                nNod+=1;            
                primerestado(W);
            }else{
                if(resultado==null){
                    resultado=nodoActual[0];
                    nodoAnteriro = nodoActual;
                    nNod+=1;
                    primerestado(W);
                }
                nodoAnteriro = nodoActual;
                nNod+=1;
                primerestado(W);
            }

        }   
        boolean noEsNuevo = EstadosNuevos.contains(resultado);
        if(!noEsNuevo){
            EstadosNuevos.add(resultado);
            tbm.addRow(fila);
            tbm.setValueAt(resultado.replace(" ", ""), filatabla, tbm.getColumnCount()-1);
            tbm.setValueAt(letraAbecedario(filatabla), filatabla, 0);// Quitamos q y filatabla va a ser abecedario
            
            
            if(EstadosNuevos.size()<=1){
                estadoInicial=letraAbecedario(filatabla);//Quitamos q y filatabla es abecedario(es entero)
            }
            filatabla+=1;
        }
        return  resultado;
    }

    private void crearcerradura(String composicion, String[] alfabeto){
        String cerradura="";
        if(nNAlfa<numeroAlfabeto){//nNAlfa es para usar 'a' primero y despues 'b'
            String[] separaContiene = composicion.split(",");
            for(int i=0; i<W.length; i++){ 
                String[] nodo;
                nodo = W[i].split(",");
                for(int j=0; j<separaContiene.length; j++){// recorremos estados
                    if(nodo[0].equals(separaContiene[j])){// comparamos estado primer estado
                        if(nodo[1].equals(alfabeto[nNAlfa])){// comparamos letra alfabeto
                            if(!cerradura.equals("")){
                                cerradura = cerradura.trim()+nodo[2].trim()+",";
                            }else{
                                cerradura= nodo[2]+",";
                            }
                        }                        
                    }
                }               
            }
        nNAlfa+=1;
        String columna = estados(W, cerradura);//columna son todos los estados a donde va lambda
        n=0;
        if(!columna.equals("")){
            columna=columna.substring(0, columna.length()-1);//le quitamos la ultima coma
        }
            
        boolean noEsNuevo = EstadosNuevos.contains(columna);
        if(!noEsNuevo){
            tbm.addRow(fila);
            tbm.setValueAt(columna.replace(" ", ""), filatabla, tbm.getColumnCount()-1);//ordenar numericamente y pasar a letras      
            tbm.setValueAt(letraAbecedario(filatabla), filatabla, 0);//Quitar q y filatabla que sea abecedario                         
            filatabla+=1;
            EstadosNuevos.add(columna);            
        }                
        String nQ = tbm.getValueAt(tbm.getRowCount()-1, tbm.getColumnCount()-1).toString();
        String Qn;
        if(true){
            if(EstadosNuevos.contains(columna)){
                for(int i=0; i<tbm.getRowCount(); i++){                            
                    String iq = tbm.getValueAt(i, tbm.getColumnCount()-1).toString();
                    if(columna.equals(iq)){// revisamos si ya existe esta composicion para no repetir
                        Qn = tbm.getValueAt(i, 0).toString();
                        Qn = Qn.replace("*", "");
                        tbm.setValueAt(Qn, numeroFila, nNAlfa); //cambiar estado1 a letra
                    }
                }
            }
        }  
            if(nNAlfa>=2){
                numeroFila++;
            }  
            crearcerradura(composicion, alfabeto);
        }        
    }

    
    private void agregarAlfabeto(String[] F){
        tbm.addColumn("");
        for(int i=0; i<F.length; i++){
            tbm.addColumn(F[i]);
        }
        tbm.addColumn("Composicion");
    }
    private void validacionQuintupla(){
//        String [] arregloEstados= new String[100];
        EstadosNuevos = convertirEstadosNativos(misEstadosNativos,misEstadosNumeros,EstadosNuevos);
        for(int i=0;i<EstadosNuevos.size(); i++){
            estadosQ= estadosQ+","+(ESTADOS_ALFABETO[i]);
        String A = EstadosNuevos.get(i);
        estadoAceptacionAFN = estadoAceptacionAFN.trim();
            if (estadoAceptacionAFN.contains("10")){
                String [] abc = estadoAceptacionAFN.split(",");
            for (int j=0; j<abc.length; j++){
                if (abc[j].equals("10")){
                    abc[j]= "x";
                }
                if(A.contains(abc[j])){
                    if(!EstadoAceptacion.contains(ESTADOS_ALFABETO[i])){
                    EstadoAceptacion=EstadoAceptacion+","+(ESTADOS_ALFABETO[i]);
                    }
                }
                
            }
            } else {
                String [] abc = estadoAceptacionAFN.split("");
                for (int j=0; j<abc.length; j++){
                if(A.contains(abc[j])){
                    if(!EstadoAceptacion.contains(ESTADOS_ALFABETO[i])){
                    EstadoAceptacion=EstadoAceptacion+","+(ESTADOS_ALFABETO[i]);
                    }
                }
                
            }
            }
            
        }
        EstadoAceptacion=EstadoAceptacion.substring(1);
        setEr(EstadoAceptacion);
        estadosQ = estadosQ.substring(1);
        Vista.jTextArea1.setText(
                "Q = {"+estadosQ+
                "}\nF = {"+alfabetoF+
                "}\ni = "+estadoInicial+
                "\nA = {"+EstadoAceptacion+"}");
        
        for (int j = 0 ; j < tbm.getRowCount();j++){
        if (tbm.getValueAt(j, 3) != null || tbm.getValueAt(j, 3) != ""){
          tbm.setValueAt(getComposicionNativa(tbm.getValueAt(j, 3).toString(),misEstadosNumeros,misEstadosNativos), j, 3);
            
        }
        }
        
        
    }
    
    public static String er;

    public static String getEr() {
        return er;
    }

    public static void setEr(String er) {
        Controlador.er = er;
    }
public void cadenaAceptacionPublica(String [] alfabeto){
    cadenaAceptacionFuncion(alfabeto);
}
    
private void cadenaAceptacionFuncion(String [] alfabeto){
   String variableValidacion=validarCadenaTxt;
        String charAString="";
        String siguienteEstado="";
        boolean esAceptacion=true;
        /*validar si viene vacia la cadena*/
        if(variableValidacion.length()> 0 ){
        for(int a = 0 ; a<variableValidacion.length();a++){
            /* Empieza en el estado inicial */
            int contador=0;
            for(int b=1; b< tbm.getColumnCount()-1;b++){
                if(alfabeto[b-1].equals(Character.toString(variableValidacion.charAt(a)))){
                    contador++;
                }
            }
            if (contador==0){
                esAceptacion=false;
            }
            if(a == 0){
                for(int b=1; b< tbm.getColumnCount()-1;b++){
                    if(alfabeto[b-1].equals(Character.toString(variableValidacion.charAt(a)))){
                        if(tbm.getValueAt(0,b) != null){
                            siguienteEstado = (String) tbm.getValueAt(0,b);
                        }
                        else{
                            esAceptacion=false;
                        }
                    }                    
                }
            } else {
                for(int fila=0; fila< tbm.getRowCount();fila++){
                    if(siguienteEstado.equals(tbm.getValueAt(fila,0))){
                        for(int b=1; b< tbm.getColumnCount()-1;b++){
                            if(alfabeto[b-1].equals(Character.toString(variableValidacion.charAt(a)))){
                                if(tbm.getValueAt(fila,b) != null){
                                    siguienteEstado = (String) tbm.getValueAt(fila,b);
                                    fila = tbm.getRowCount();
                                }else{
                                    esAceptacion=false;
                                }
                            }
                        }
                    
                }}
            }
        }
        } 
        if (variableValidacion.length()== 0){
            setVariableGlobal("Porfavor ingrese un valor");
        }else {
        if(esAceptacion){
            if(getEr().contains(siguienteEstado)){
            setVariableGlobal("Es cadena de aceptacion");
        }else{
            setVariableGlobal("No es cadena de aceptacion");
        }
        }else{
            setVariableGlobal("No es cadena de aceptacion");
        }
        }
        
      
        
        
           
}

public String letraAbecedario(int estado){
    return estado >= 0 && estado < 27 ? String.valueOf((char)(estado + 65)) : "se paso"+estado;
}

public String letraAbecedarioString(String estadoLetras){
    String estado_limpio = estadoLetras.replace("estado","");
    estado_limpio = estado_limpio.replaceAll("\\s+","");
    estado_limpio = estado_limpio.replace(",", "");
    String estadoFinal= "";
    for (char a : estado_limpio.toCharArray()){
        estadoFinal=estadoFinal+ letraAbecedario(Character.getNumericValue(a)) +",";
    }
    if(estadoFinal != ""){
        estadoFinal = estadoFinal.substring(0, estadoFinal.length()-1);
    }
    return estadoFinal;
    
}

public void modificarEstadosALetras(){
    
    // ordenando numeros de tabla
      for(int i=0;i<tbm.getRowCount(); i++){
            String[] a = tbm.getValueAt(i, tbm.getColumnCount()-1).toString().split(",");
            int temporal = 0, varUno, varDos;
            String temporalString;
            for (int j = 0; j < a.length; j++) {
                for (int k = 1; k < (a.length - j); k++) {
                    if ("xyz".contains(a[k]) || "xyz".contains(a[k-1])){
                        varUno = devuelveValorEntero(a[k]);
                        varDos = devuelveValorEntero(a[k-1]);
                        if ( varDos > varUno){
                            temporalString = a[k - 1];
                            a[k - 1] = a[k];
                            a[k] = temporalString;
                        }
                    }
                    else if (Integer.parseInt(a[k - 1]) > Integer.parseInt(a[k])) {
                        temporal = Integer.parseInt(a[k - 1]);
                        a[k - 1] = a[k];
                        a[k] = String.valueOf(temporal);
                    }
                }
            }
            String otro = Arrays.toString(a);
            otro = otro.replace("[", "");
            otro = otro.replace("]", "");
            
            tbm.setValueAt(otro, i, tbm.getColumnCount()-1);
        }
//    tbm.getValueAt(tbm.getRowCount()-1, tbm.getColumnCount()-1).toString();
    for (int i = 0 ; i < tbm.getRowCount();i++){
        if (tbm.getValueAt(i, 3) != null || tbm.getValueAt(i, 3) != ""){
            tbm.setValueAt(letraAbecedarioStringDos(tbm.getValueAt(i, 3).toString()), i, 3);
        }
    }
}

public String letraAbecedarioStringDos(String estadoLetras){
    String estado_limpio = estadoLetras.replace("estado","");
    estado_limpio = estado_limpio.replaceAll("\\s+","");
    estado_limpio = estado_limpio.replace(",", "");
    String estadoFinal= "";
    for (char a : estado_limpio.toCharArray()){
        estadoFinal=estadoFinal+ (String.valueOf(a)) +",";
    }
    if(estadoFinal != ""){
        estadoFinal = estadoFinal.substring(0, estadoFinal.length()-1);
    }
    return estadoFinal;
    
}

    private String ordenarResultado(String result){
            String[] a = result.split(",");
            int temporal = 0;
            String temporalString="";
            int varUno, varDos;
            for (int j = 0; j < a.length; j++) {
                for (int k = 1; k < (a.length - j); k++) {
                    if ("xyz".contains(a[k]) || "xyz".contains(a[k-1])){
                        varUno = devuelveValorEntero(a[k]);
                        varDos = devuelveValorEntero(a[k-1]);
                        if ( varDos > varUno){
                            temporalString = a[k - 1];
                            a[k - 1] = a[k];
                            a[k] = temporalString;
                        }
                    } 
                    else if (Integer.parseInt(a[k - 1]) > Integer.parseInt(a[k])) {
                        temporal = Integer.parseInt(a[k - 1]);
                        a[k - 1] = a[k];
                        a[k] = String.valueOf(temporal);
                    }
                }
            }
            String otro = Arrays.toString(a);
            otro = otro.replace("[", "");
            otro = otro.replace("]", ",");
            otro = otro.replace(" ", "");
            
            
            return otro;
        
    }
    
    public int devuelveValorEntero(String valor){
        int numeroEntero= 0;
        switch(valor){
            case "x": numeroEntero=10;
            break;
            case "y": numeroEntero=11;
            break;
            case "z": numeroEntero=12;
            break;
            default: numeroEntero = Integer.parseInt(valor);
            break;
        }
        return numeroEntero;
    }

    public String getVariableGlobal() {
        return variableGlobal;
    }

    public void setVariableGlobal(String variableGlobal) {
        this.variableGlobal = variableGlobal;
    }
}