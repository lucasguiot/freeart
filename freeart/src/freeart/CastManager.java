package freeart;

import java.util.ArrayList;
import java.util.List;


public class CastManager {
	
	public static ArrayList<Catalogue> castCatalogue(List<Object[]> list)
	{
		ArrayList<Catalogue> res = new ArrayList<Catalogue>();
		for(Object[] o : list)
		{
			res.add(new Catalogue((int)o[0], (String)o[1], (String)o[2]));
		}
		return res;
	}

}
