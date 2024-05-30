import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

class Polynomial{
	double[] coeffs;
	int[] expo;
	public Polynomial(){
		double[] zero = {0};
		coeffs = zero;
		int[] power_zero = {0};
		expo = power_zero;
	}
	public Polynomial(double[] coefficient, int[] exponent){
		coeffs = coefficient;
		expo = exponent;
	}

	public Polynomial(File myfile){
		try {
			Scanner in = new Scanner(myfile);
			String data = in.nextLine();
			String expression = "";

			String minus = "(?i)-";
			expression = data.replaceAll(minus, "+-");

			if(expression.charAt(0) == '+'){
				expression = expression.substring(1);
			}
			String[] array = expression.split("[+]");
			String[][] all_sep = new String[array.length][2];
			for(int u = 0; u < array.length; u++){
				all_sep[u] = array[u].split("[x]");
			}

			int count = 0;

			double[] coefficient = new double[array.length];
			int[] exponent = new int[array.length];

// Exceptions of power 1 and power 0
			if(all_sep[0].length == 1){
				int length = array[0].length() - 1;
				coefficient[0] = Double.parseDouble(all_sep[0][0]);

				if(array[0].charAt(length) == 'x'){
					exponent[0] = 1;
				} else{
					exponent[0] = 0;
				}

				count++;
				if(all_sep[1].length == 1){
					coefficient[1] = Double.parseDouble(all_sep[1][0]);
					exponent[1] = 1;
					count++;
				}
			}
////////////////////////////////////////////////////
			while(count < array.length){
				coefficient[count] = Double.parseDouble(all_sep[count][0]);
				exponent[count] = Integer.parseInt(all_sep[count][1]);
				count++;
			}

			expo = exponent;
			coeffs = coefficient;

			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}


	private boolean inArray(int[] array, int power) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == power)
				return true;
		}
		return false;
	}
	
	
	public Polynomial add(Polynomial poly){

		//Get length of the array
		int size = 0;
		for(int z = 0; z < expo.length; z++) {
			if(!(inArray(poly.expo, expo[z]))) {
				size += 1;
			}
		}
		size += poly.expo.length;
		int[] add_expo = new int[size];
		double[] add_coeff = new double[size];

		int this_count = 0;
		int poly_count = 0;
		for(int s = 0; s < size; s++){

			if(this_count >= expo.length){
				add_expo[s] = poly.expo[poly_count];
				add_coeff[s] = poly.coeffs[poly_count];
				poly_count++;
				continue;
			}
			if(poly_count >= poly.expo.length){
				add_expo[s] = expo[this_count];
				add_coeff[s] = coeffs[this_count];
				this_count++;
				continue;
			}

			if(expo[this_count] < poly.expo[poly_count]){
				add_expo[s] = expo[this_count];
				add_coeff[s] = coeffs[this_count];
				this_count++;
			}

			else if(expo[this_count] == poly.expo[poly_count]){
				add_coeff[s] = poly.coeffs[poly_count] + coeffs[this_count];
				add_expo[s] = expo[this_count];
				poly_count++;
				this_count++;
			}

			else{
				add_expo[s] = poly.expo[poly_count];
				add_coeff[s] = poly.coeffs[poly_count];
				poly_count++;
			}

		}

/////////////////////////////////////////////////////
//		Removing redundant vectors
/////////////////////////////////////////////////////

		int final_size = 0;
		for(int z = 0; z < size; z++){
			if(add_coeff[z] != 0){
				final_size++;
			}
		}

		if(final_size == 0){
			return new Polynomial();
		}

		int[] final_expo = new int[final_size];
		double[] final_coeffs = new double[final_size];

		int final_count = 0;

		for(int y = 0; y < size; y++){
			if(add_coeff[y] != 0){
				final_expo[final_count] = add_expo[y];
				final_coeffs[final_count] = add_coeff[y];
				final_count++;
			}
		}

		return new Polynomial(final_coeffs, final_expo);
	}

////////////////////////////////////////////////////////////////////

	public Polynomial multiply(Polynomial poly){

		int[] temp_expo = new int[expo.length];
		double[] temp_coeff = new double[expo.length];


		Polynomial temp = new Polynomial(temp_coeff, temp_expo);

		double[] product_coeff = {0};
		int[] product_expo = {expo[0]+poly.expo[0]};
		Polynomial product = new Polynomial(product_coeff, product_expo);

		for(int i = 0; i < poly.expo.length; i++){
		
			for(int j = 0; j < expo.length; j++){
				temp.coeffs[j] = poly.coeffs[i]*coeffs[j];
				temp.expo[j] = poly.expo[i] + expo[j];
			}
		
			product = product.add(temp);
		
		}

		return product;
	}


////////////////////////////////////////////////////////////////
	public double evaluate(double x){
		int size = coeffs.length;
		double sum = 0;
		for(int i = 0; i < size; i++){
			sum = sum + (coeffs[i] * Math.pow(x, expo[i]));
		}
		return sum;
	}

///////////////////////////////////////////////////////////////////////
	public boolean hasRoot(double x){
		if(this.evaluate(x) == 0){
			return true;
		}
		return false;
	}

	public void saveToFile(String path){
		String equation = "";
		for(int i = 0; i < expo.length - 1; i++){
			if(expo[i] == 0){
				equation = equation + coeffs[i];
			}
			else if(expo[i] == 1){
				equation = equation + coeffs[i] + "x";
			}
			else{
			equation = equation + coeffs[i] + "x" + expo[i];
			}

			if(coeffs[i+1] > 0){
				equation = equation + "+";
			}
		}
		equation = equation + coeffs[expo.length-1] + "x" + expo[expo.length-1];

		try{
			FileWriter myWriter = new FileWriter(path);
			myWriter.write(equation);
			myWriter.close();
			return;
		} catch (IOException e){
			e.printStackTrace();
		}

	}
	
}