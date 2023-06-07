import java.util.Arrays;

public class Problem3 {
    public static void main(String[] args) {
        //Don't edit anything here
        Integer[] a1 = new Integer[]{-1, -2, -3, -4, -5, -6, -7, -8, -9, -10};
        Integer[] a2 = new Integer[]{-1, 1, -2, 2, 3, -3, -4, 5};
        Double[] a3 = new Double[]{-0.01, -0.0001, -.15};
        String[] a4 = new String[]{"-1", "2", "-3", "4", "-5", "5", "-6", "6", "-7", "7"};
        
        bePositive(a1);
        bePositive(a2);
        bePositive(a3);
        bePositive(a4);
    }
    static <T> void bePositive(T[] arr){
        System.out.println("Processing Array:" + Arrays.toString(arr));
        //your code should set the indexes of this array
        Object[] output = new Object[arr.length];
            //using an if statment to check and see if the data type is an integer,double,or string
            // base on the component type
            //this statment for loop iterate through each value
            //providing the absolute number for each value, resulting into a positive number
        if(arr.getClass().getComponentType() == Integer.class) {
            for (int x=0; x< arr.length; x++){
                output[x] = Math.abs((Integer) arr[x]);
            }
        }  else if (arr.getClass().getComponentType() == Double.class) {  
            for (int x=0; x< arr.length; x++){
                output[x] = Math.abs((Double) arr[x]);
            }
        }  else if (arr.getClass().getComponentType() == String.class) {
            for (int x = 0; x< arr.length; x++){
                //a parsing method is created in attempt to convert the string to an integer
                try { 
                    int Str = Integer.parseInt((String) arr[x]);
                output[x] =String.valueOf(Math.abs(Str));
            }
            //if value cannot be convert an NumberFormatException is thrown
            catch (NumberFormatException e) {
                output[x] = arr[x];
            }
        }

    }
   

        //TODO convert each value to positive
        //set the result to the proper index of the output array
        //hint: don't forget to handle the data types properly
        
        //end edit section

        StringBuilder sb = new StringBuilder();
        for(Object i : output){
            if(sb.length() > 0){
                sb.append(",");
            }
            sb.append(String.format("%s (%s)", i, i.getClass().getSimpleName().substring(0,1)));
        }
        System.out.println("Result: " + sb.toString());
    }
}