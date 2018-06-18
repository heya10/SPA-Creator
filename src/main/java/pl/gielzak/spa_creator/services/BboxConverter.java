package pl.gielzak.spa_creator.services;

public class BboxConverter {
	
	public static String convert(String latitude, String longitude){
		try{
			Double latitudeD = Double.parseDouble(latitude);
			Double longitudeD = Double.parseDouble(longitude);
			
			Double left,bottom,right,top;
			left = latitudeD-0.004;
			right = latitudeD+0.004;
			bottom = longitudeD-0.004;
			top = longitudeD+0.004;	
			
			return ""+left+","+bottom+","+right+","+top;
			
		}catch(Exception ex){
			//default value
			return "-74.0746%2C40.6721%2C-74.0111%2C40.7055";
		}
	}

}
