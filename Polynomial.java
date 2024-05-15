class Polynomial{
	double[] coeffs;
	public Polynomial(){
		double[] zero = {0};
		coeffs = zero;
	}
	public Polynomial(double[] array){
		coeffs = array;
	}
	public Polynomial add(Polynomial array){
		int size = this.coeffs.length;
		double[] added = array.coeffs;
		if(array.coeffs.length < size){
			size = array.coeffs.length;
			added = this.coeffs;
			for(int i = 0; i < size; i++){
				added[i] += array.coeffs[i];
			}
		} else {
			for(int i = 0; i < size; i++){
				added[i] += this.coeffs[i];
			}
		}
		Polynomial sum = new Polynomial(added);
		return sum;
	}
	public double evaluate(double x){
		int size = coeffs.length;
		double sum = 0;
		for(int i = 0; i < size; i++){
			sum = sum + (coeffs[i] * Math.pow(x, i));
		}
		return sum;
	}
	public boolean hasRoot(double x){
		if(this.evaluate(x) == 0){
			return true;
		}
		return false;
	}
}
