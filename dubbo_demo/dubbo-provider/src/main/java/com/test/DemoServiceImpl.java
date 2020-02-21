package com.test;
import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoServiceImpl implements DemoService{

	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return name;
	}
	public int Use(int i) {
		return i + 5 ;
	}
	public String get_weather(String name)
	{
		String wea;
		JuheConnecter net_connector = new JuheConnecter();
        //上述为json得到城市id

		database b = new database();
		String id = b.getid(name);

		if(id==null)
			wea = net_connector.getWeaRequest(id);
		else
		{
			String a;
			a = b.Is_have(id);
			if(a.equals("0")){
				wea = net_connector.getWeaRequest(id);
				b.increase(id,wea);
			}
			else if(a.equals("1")){
				wea = net_connector.getWeaRequest(id);
				b.update(wea,id);
			}
			else
			{
				System.out.println("使用缓存");
				wea = a;
			}
		}
		return wea;
	}
}


