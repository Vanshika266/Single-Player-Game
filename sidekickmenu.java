import java.util.*;

import java.util.Scanner;
import java.util.Random;

class Node
{
	int id;
	int unique_id=0;
	private Monster monster_node;
	
	Node()
	{
		id = unique_id;
		unique_id++;
	}
	
	Node(int d)
	{
		id = d;
		
	}
	
	void SetMonsterNode(Monster m3)
	{
		monster_node=m3;
	}
	
	Monster GetMonsterNode()
	{
		return monster_node;
	}
}


class Map
{
	int vertices=12;

	ArrayList<ArrayList<Node>> design = new ArrayList<ArrayList<Node>>(vertices);
	
	ArrayList<Node> all_nodes = new ArrayList<Node>();
	Map()
	{
		for(int i=0;i<13;i++)
		{

			ArrayList<Node> small = new ArrayList<Node>();
			design.add(small);
			Node curr = new Node(i);
			all_nodes.add(curr);
		}
		
		//design.AddPath(all_nodes.get(1),all_nodes.get(2));
		
		design.get(1).add(new Node(2));
		design.get(1).add(new Node(5));
		design.get(1).add(new Node(11));

	//	design.get(2).add(all_nodes.get(1));
		design.get(2).add(new Node(3));
		design.get(2).add(new Node(6));
		
		design.get(3).add(new Node(2));
		design.get(3).add(new Node(4));
		design.get(3).add(new Node(5));

		design.get(4).add(new Node(3));
		design.get(4).add(new Node(10));
		design.get(4).add(new Node(12));

		//design.get(5).add(new Node(1));
		design.get(5).add(new Node(3));
		design.get(5).add(new Node(9));

		design.get(6).add(new Node(2));
		design.get(6).add(new Node(7));
		design.get(6).add(new Node(8));
		
		design.get(7).add(new Node(4));
		design.get(7).add(new Node(6));

		design.get(8).add(new Node(6));
		
		design.get(9).add(new Node(5));
		design.get(9).add(new Node(10));
		design.get(9).add(new Node(11));

		design.get(10).add(new Node(4));
		design.get(10).add(new Node(9));
		
		design.get(11).add(new Node(9));

		design.get(12).add(new Node(4));
		
		
		for(int j=2;j<all_nodes.size()-1;j++)
		{
			Random big = new Random();
			int num = big.nextInt(3);
			if(num==0)
			{
				all_nodes.get(j).SetMonsterNode(new Goblins());
			}
			else if(num==1)
			{
				all_nodes.get(j).SetMonsterNode(new Zombies());
			}
			else if(num==2)
			{
				all_nodes.get(j).SetMonsterNode(new Fiends());
			}
			
		}
		
		all_nodes.get(12).SetMonsterNode(new Lionfang());
		
	}
	
	void AddPath(Node u,Node v)
	{
		design.get(u.id).add(v);
		design.get(v.id).add(u);
	}



}

abstract class Hero
{

	int level;
	int max_hp;
	private double HXP;
	private double HHP;
	//int defence_points;
	int hero_moves;
	String heroname;
	
	int Hero_AttackAttribute=0;
	int Hero_DefenceAttribute=0;
	int Hero_SPCOUNT;
	boolean Hero_Magic;
	boolean save;
	boolean withSK;
	SideKick ChosenSK;
	
	int cloned=0;
	boolean check_clone=false;
	
	ArrayList <SideKick> AllSideKick = new ArrayList<SideKick>();
	
	int Special_moves=0;
	
	double GetHeroHP()
	{
		return this.HHP;
	}
	void SetHeroHP(double hp)
	{
		this.HHP = hp;
	}
	
	double GetHeroXP()
	{
		return this.HXP;
	}
	void SetHeroXP(double xp)
	{
		this.HXP = xp;
	}
	
	void SetHeroName(String hn)
	{
		heroname=hn;
		level=1;
		// Setting default values for XP and HP
		HHP = 100;
		HXP = 0;
		hero_moves=0;
		max_hp = 100;
		Hero_Magic = false;
		withSK=false;
	}
	
	public void Attack(Monster m)
	{

		System.out.println("You Choose To Attack");
		System.out.println("You attacked and inflicted "+this.Hero_AttackAttribute+" damage to the monster");
		if(this.check_clone==true)
		{
			SideKick present = this.AllSideKick.get(0);
			m.SetMHP(m.GetMHP()-present.sidekick_attack);
			
			System.out.println("Sidekick attacked and inflicted "+present.sidekick_attack+" damage to the monster");
			ArrayList <SideKick>total_sk = present.clone_side;
			
			for(int y=0;y<3;y++)
			{
				SideKick cloned_now = total_sk.get(y);
				m.SetMHP(m.GetMHP()-cloned_now.sidekick_attack);
				System.out.println("Sidekick attacked and inflicted "+cloned_now.sidekick_attack+" damage to the monster");
			}
			
			System.out.println("Sidekick HP: "+present.GetSHP()+"/"+present.maxSH);
		
			for(int y=0;y<3;y++)
			{
				SideKick cloned_now = total_sk.get(y);
				System.out.println("Sidekick HP: "+cloned_now.GetSHP()+"/"+cloned_now.maxSH);
			}
			
		}
		
		System.out.println("Your HP: "+this.GetHeroHP()+"/"+this.max_hp+" Monsters HP: "+m.GetMHP()+"/"+m.max_monster_hp);
	}
	public void Defence(Monster m)
	{
		System.out.println("You Choose To Defend");
		System.out.println("Monster attack reduced by "+this.Hero_DefenceAttribute);
		System.out.println("Your HP: "+this.GetHeroHP()+"/"+this.max_hp+" Monsters HP: "+m.GetMHP()+"/"+m.max_monster_hp);
	}
	public abstract void SpecialPower(Monster m);
	
}

/*
 *  XP - level up attribute
 *  HP - health indicator
 */
class Warrior extends Hero
{
	@Override
	public void Attack(Monster m1)
	{
		
		this.Hero_AttackAttribute = 10;
		if(this.Hero_Magic==true)
		{
			this.Hero_AttackAttribute = 15;
		}
		m1.SetMHP((m1.GetMHP()-this.Hero_AttackAttribute));
		if(m1.GetAttackValue()<0)
		{
			m1.SetAttackValue(0);
		}
		if(m1.GetMHP()<=0)
		{
			m1.SetMHP(0);
		}
		super.Attack(m1);
	}

	@Override
	public void Defence(Monster m2)
	{
		this.Hero_DefenceAttribute = 3;
		if(this.Hero_Magic==true)
		{
			this.Hero_DefenceAttribute = 8;
		}
		m2.SetAttackValue(m2.GetAttackValue()-this.Hero_DefenceAttribute);
		if(m2.GetAttackValue()<0)
		{
			m2.SetAttackValue(0);
		}
		
		save=true;
		super.Defence(m2);
	}
	

	public void SpecialPower(Monster m)
	{
		this.Hero_AttackAttribute+=5;
		this.Hero_DefenceAttribute+=5;

		System.out.println("Special Power Activated");
		
		/*.Hero_Magic=true;
		this.Special_moves++;
		if(Special_moves>3)
		{
			this.Special_moves=0;
		}*/
	}
}
class Thief extends Hero
{
	@Override
	public void Attack(Monster m1)
	{
		this.Hero_AttackAttribute = 6;
	/*	if(this.Hero_Magic==true)
		{
			this.Hero_AttackAttribute = 10;
		}
		*/
		m1.SetMHP((m1.GetMHP()-this.Hero_AttackAttribute));
		if(m1.GetAttackValue()<0)
		{
			m1.SetAttackValue(0);
		}
		if(m1.GetMHP()<=0)
		{
			m1.SetMHP(0);
		}
		super.Attack(m1);
	}

	@Override
	public void Defence(Monster m2)
	{
		this.Hero_DefenceAttribute = 4;
		/*if(this.Hero_Magic==true)
		{
			this.Hero_DefenceAttribute = 8;
		}*/
	/*	m2.SetAttackValue(m2.GetAttackValue()-this.Hero_DefenceAttribute);
		if(m2.GetAttackValue()<0)
		{
			m2.SetAttackValue(0);
		}
*/		save=true;
		super.Defence(m2);
		
	}

	@Override
	public void SpecialPower(Monster m)
	{
		double mpower = m.GetMHP();
		double less = (30*mpower)/100;
		if(less<0)
		{
			less=0;
		}
		m.SetMHP(mpower - less);
		this.SetHeroHP(this.GetHeroHP() + less);
		System.out.println("Special Power Activated");
		System.out.println("Performing Special Attack");
		System.out.println("You Have Stolen "+less+" HP From Monster");
		System.out.println("Your HP: "+this.GetHeroHP()+"/"+this.max_hp+" Monsters HP: "+m.GetMHP()+"/"+m.max_monster_hp);
	}
}
class Mage extends Hero
{
	@Override
	public void Attack(Monster m1)
	{
		this.Hero_AttackAttribute = 6;
		if(this.Hero_Magic==true)
		{
			this.Hero_AttackAttribute = 11;
		}
		m1.SetMHP((m1.GetMHP()-this.Hero_AttackAttribute));
		if(m1.GetAttackValue()<0)
		{
			m1.SetAttackValue(0);
		}
		if(m1.GetMHP()<=0)
		{
			m1.SetMHP(0);
		}
		super.Attack(m1);
	}

	@Override
	public void Defence(Monster m2)
	{
		
		this.Hero_DefenceAttribute = 4;
		if(this.Hero_Magic==true)
		{
			this.Hero_DefenceAttribute = 9;
		}
		
		this.SetHeroHP(this.GetHeroHP()+this.Hero_DefenceAttribute);
		/*
		m2.SetAttackValue(m2.GetAttackValue()-this.Hero_DefenceAttribute);
		if(m2.GetAttackValue()<0)
		{
			m2.SetAttackValue(0);
		}
		*/
		save=true;
		super.Defence(m2);
	}
	
	@Override
	public void SpecialPower(Monster m)
	{
		double dec = (5*m.GetMHP());
		m.SetMHP(m.GetMHP()-dec);
		
	}
	
}
class Healer extends Hero
{
	@Override
	public void Attack(Monster m1)
	{
		this.Hero_AttackAttribute = 4;
		if(this.Hero_Magic==true)
		{
			this.Hero_AttackAttribute = 9;
		}
		m1.SetMHP((m1.GetMHP()-this.Hero_AttackAttribute));
		if(m1.GetAttackValue()<0)
		{
			m1.SetAttackValue(0);
		}
		if(m1.GetMHP()<=0)
		{
			m1.SetMHP(0);
		}
		super.Attack(m1);
		/*
		System.out.println("You Choose To Attack");
		System.out.println("You attacked and inflicted "+this.Hero_AttackAttribute+" damage to the monster");
		System.out.println("Your HP: "+this.GetHeroHP()+"/"+this.max_hp+" Monsters HP: "+m1.GetMHP()+"/"+m1.max_monster_hp);
		*/
	}

	@Override
	public void Defence(Monster m2)
	{
		this.Hero_DefenceAttribute = 8;
		if(this.Hero_Magic==true)
		{
			this.Hero_DefenceAttribute = 13;
		}
		m2.SetAttackValue(m2.GetAttackValue()-this.Hero_DefenceAttribute);
		if(m2.GetAttackValue()<0)
		{
			m2.SetAttackValue(0);
		}

		save=true;
		super.Defence(m2);
	}
	
	@Override
	public void SpecialPower(Monster m)
	{
		double inc = (5*this.GetHeroHP())/100;
		this.SetHeroHP(this.GetHeroHP()+inc);
		
	}
	
}

class Monster
{
	private double MHP;
	int level;
	int monster_moves;
	int max_monster_hp;
	String monster_name;
	
	Monster()
	{
		monster_moves=0;
	}
	void SetMHP(double hp)
	{
		MHP = hp;
	}
	double GetMHP()
	{
		return MHP;
	}
	
	int GetAttackValue()
	{
		return 0;
	}
	void SetAttackValue(int val)
	{
	//	return 
	}
	
	void AttackHero(Hero h1)
	{

		System.out.println("Monster Attack");
		Random rm = new Random();
		double basic = Math.random();
		
		double diff=0;
		if(basic<0.1 && this.monster_name=="Lionfang")
		{
			diff = h1.GetHeroHP()/2;
			h1.SetHeroHP(h1.GetHeroHP()/2);
		}
		
		else
		{
			double move = rm.nextGaussian();
			
			double compare = this.GetMHP()/4;
			
			
			double affectvalue = (((move*compare)/6)+(compare/2));
			

			// Value printed is after the decreasing the defense value of the hero (ex: thief defense)
			diff = affectvalue;
			if(h1.save==true)
			{
				diff=diff-h1.Hero_DefenceAttribute;
				h1.save=false;
			}
			//h1.Hero_DefenceAttribute=0;
			
			h1.SetHeroHP(h1.GetHeroHP()-affectvalue);

		}
		
		System.out.println("The monster attacked and inflicted "+diff+" damage to you");
		System.out.println("Your HP: "+h1.GetHeroHP()+"/"+h1.max_hp+" Monster HP: "+this.MHP+"/"+this.max_monster_hp);
		if(h1.check_clone==true)
		{
			SideKick present = h1.AllSideKick.get(0);
			double value_reduced = (15*diff)/100;
			
			present.SetSHP(present.GetSHP()-value_reduced);
			
			for(int y=0;y<4;y++)
			{
				
				System.out.println("Sidekick HP: "+present.GetSHP()+"/"+present.maxSH);
			}
		}
		else if(h1.withSK==true)
		{
			SideKick present = h1.AllSideKick.get(0);
			double value_reduced = (15*diff)/100;
			
			present.SetSHP(present.GetSHP()-value_reduced);
			System.out.println("Sidekick HP: "+present.GetSHP()+"/"+present.maxSH);
			
		}
		
	}
}

class Goblins extends Monster
{
	Goblins()
	{
		this.SetMHP(100);
		this.level=1;
		this.max_monster_hp=100;
		this.monster_name="Goblins";
		
	}
	void SetMHP()
	{
		this.SetMHP(100);
		this.level=1;
		this.max_monster_hp=100;
		this.monster_name="Goblins";
	}
}
class Zombies extends Monster
{
	Zombies()
	{
		
		this.SetMHP(150);
		this.level=2;
		this.max_monster_hp=150;
		this.monster_name="Zombies";
	}
	void SetMHP()
	{
		this.SetMHP(150);
		this.level=2;
		this.max_monster_hp=150;
		this.monster_name="Zombies";
	}
	
	@Override
	void AttackHero(Hero h1)
	{
		if(h1.withSK==true)
		{
			h1.Hero_DefenceAttribute = h1.Hero_DefenceAttribute+5;
		}
		super.AttackHero(h1);
		
	}
}
class Fiends extends Monster
{
	
	Fiends()
	{
		
		this.SetMHP(200);
		this.level=3;
		this.max_monster_hp=200;
		this.monster_name = "Fiends";
	}
	void SetMHP()
	{
		this.SetMHP(200);
		this.level=3;
		this.max_monster_hp=200;
		this.monster_name = "Fiends";
	}
}
class Lionfang extends Monster
{
	Lionfang()
	{
		this.SetMHP(250);
		this.level=4;
		this.max_monster_hp=250;
		this.monster_name = "Lionfang";
	
	}
	void SetMHP()
	{
		this.SetMHP(250);
		this.level=4;
		this.max_monster_hp=250;
		this.monster_name = "Lionfang";
	}
}


class GameLayout
{
	void LocationMenu(ArchLegends arch,User cuser,int val,Map mymap)
	{
		System.out.println("Welcome "+cuser.GetUserName());
		System.out.println("You are at the location "+val);
		ArrayList<Integer> positions = new ArrayList<Integer>();
		//positions.ge
		ArrayList<Node> minm = mymap.design.get(val);
				
		ArrayList<Integer> all = new ArrayList<Integer>();
		all.add(0);
		for(int h=0;h<minm.size();h++)
		{
			all.add(minm.get(h).id);
			System.out.println((h+1)+") Go To Location "+minm.get(h).id);
		}
		
		System.out.println("Enter -1 to exit");
		Scanner m = new Scanner(System.in);
		int var = m.nextInt();
		if(var==-1)
		{
			arch.GameMenu();
		}
		else
		{
			int va = all.get(var);
			System.out.println("Moving to location "+all.get(var));
			cuser.User_position = va;
			
			//this.FightMenu(arch,cuser,mymap,var);
			Monster fight = mymap.all_nodes.get(va).GetMonsterNode();
			
			System.out.println("Fight Started. You are fighting with a level "+fight.level+" monster");
			this.FightMenu(arch, cuser, mymap,fight,va);
			
		}
		
	}
	
	void FightMenu(ArchLegends arch1,User cuser1, Map mymap1,Monster mn,int variable)
	{
		
		Hero playing = cuser1.GetUserHero();
		
		if(playing.GetHeroHP()<=0)
		{
			System.out.println("Hero Killed");
			System.out.println("Starting from initial location");
			playing.max_hp=100;
			arch1.game_start=true;
			playing.SetHeroHP(playing.max_hp);
			playing.SetHeroXP(0);
			playing.level=1;
			playing.hero_moves=0;
			cuser1.num_moves=0;
			
			playing.withSK = false;
		//	playing.check_clone=false;
		//	playing.cloned=1;
			
			cuser1.special_activation=false;
			cuser1.special_activation_count=0;
			cuser1.user_level=1;
			cuser1.User_position=1;
			
			this.LocationMenu(arch1, cuser1, 1, mymap1);
		}
		if(mn.GetMHP()<=0 && (mn.monster_name).equals("Lionfang"))
		{
			System.out.println("You Won The Game");
			System.exit(0);
		}
		if(mn.GetMHP()<=0)
		{
			System.out.println("Monster Killed");
			int many = cuser1.user_level*20;
			playing.SetHeroHP(playing.max_hp);
			arch1.game_start=true;
			System.out.println(many+" XP Awarded");
			
			if(playing.withSK==true)
			{
				double skgift = many/10;
				playing.ChosenSK.SetSXP(playing.ChosenSK.GetSXP()+skgift);
				playing.ChosenSK.SetSHP(playing.ChosenSK.maxSH);
				System.out.println("XP Gained by SideKick");
				
			}
			
			//playing.check_clone=false;
			//playing.cloned = 1;
			
			playing.withSK = false;
			playing.SetHeroXP(playing.GetHeroXP()+20);
			
			// MONSTER CREATED AGAIN
			mn.SetMHP(mn.max_monster_hp);
			
			System.out.println("Fight Won proceeding to next location");
			this.SideMenu(arch1, cuser1, mymap1, mn, variable, playing);
			this.LocationMenu(arch1, cuser1, variable, mymap1);
		}
		
		if(cuser1.user_magic==false)
		{
			if(cuser1.num_moves==3)
			{
				cuser1.num_moves=0;
				
				cuser1.user_magic=true;
			}
		}
		
		this.MainAttack(arch1, cuser1, mymap1, mn, variable,playing);
		

	}
	
	/*public boolean StringCompare(String s2)
	{
		return s2.equals(this);
	}*/
	
	void MainAttack(ArchLegends arch1,User cuser1, Map mymap1,Monster mn,int variable,Hero playing)
	{

		Scanner t = new Scanner(System.in);
		
		if(arch1.game_start==true) {
		System.out.println("Type yes if you would like to have a sidekick else no");
		String hi = t.next();

		SideKick chosensk;
		
		if(hi.equals("yes"))
		{
			Collections.sort(playing.AllSideKick, SideKick.ChooseSideKick);
			
		//	System.out.println(playing.AllSideKick.get(0));
			
			
			if(playing.AllSideKick.size()>0)
			{
				chosensk = playing.AllSideKick.get(0);
				
				if(chosensk.GetSHP()<=0)
				{
					playing.AllSideKick.remove(0);
					if(playing.AllSideKick.size()>0)
					{
						chosensk = playing.AllSideKick.get(0);

						String press="";
						
						if(chosensk.skname.equals("minion") && playing.cloned==0)
						{
							System.out.println("Press c to use cloning ability. Else press f to move to the fight");
							press = t.next();
							if(press.equals("c"))
							{
								System.out.println("Cloning done");
								playing.cloned=1;
								playing.check_clone=true;
								chosensk.clone(chosensk);
								
							}
						}
						playing.ChosenSK = chosensk;
						System.out.println("You have a sidekick "+chosensk.skname+" with you. Attack of sidekick is: "+chosensk.sidekick_attack);
					}
					else
					{
						System.out.println("No SideKick");
					}
				}
				else
				{
					String press="";
					
				//	System.out.println(playing.cloned);
					if(chosensk.skname.equals("minion") && playing.cloned==0)
					{
						System.out.println("Press c to use cloning ability. Else press f to move to the fight");
						press = t.next();
						if(press.equals("c"))
						{
							System.out.println("Cloning done");
							playing.cloned=1;
							playing.check_clone=true;
							chosensk.clone(chosensk);
							
						}
					}
					playing.ChosenSK = chosensk;
					System.out.println("You have a sidekick "+chosensk.skname+" with you. Attack of sidekick is: "+chosensk.sidekick_attack);
					
				}

			}
			else
			{
				System.out.println("No SideKick");
			}
			
		}

		arch1.game_start=false;
		}
		System.out.println("Choose Move");
		System.out.println("1) Attack");
		System.out.println("2) Defence");
		
		if(cuser1.user_magic==true)
		{
			System.out.println("3) Special Attack");
		}
		

		cuser1.num_moves++;
		int y = t.nextInt();
		if(y==1)
		{
			if(cuser1.special_activation_count>3)
			{
				cuser1.special_activation_count=0;
				cuser1.special_activation=false;
				playing.Hero_Magic=false;
				System.out.println("Special Power Deactivated");
			}
			if(cuser1.special_activation==true)
			{
				cuser1.special_activation_count++;
				cuser1.GetUserHero().SpecialPower(mn);
			}
			cuser1.GetUserHero().Attack(mn);
			mn.AttackHero(cuser1.GetUserHero());
			this.FightMenu(arch1, cuser1, mymap1, mn,variable);
			
		}
		else if(y==2)
		{
			if(cuser1.special_activation_count>3)
			{
				cuser1.special_activation_count=0;
				cuser1.special_activation=false;
				playing.Hero_Magic=false;
				System.out.println("Special Power Deactivated");
			}
			if(cuser1.special_activation==true)
			{
				cuser1.special_activation_count++;
				cuser1.GetUserHero().SpecialPower(mn);
			}
			
			cuser1.GetUserHero().Defence(mn);
			mn.AttackHero(playing);
			this.FightMenu(arch1, cuser1, mymap1, mn,variable);
		}
		else if(y==3)
		{
			System.out.println("Special Power Activated");
			cuser1.user_magic=false;
			
			if((playing.heroname).equals("Thief"))
			{
				playing.SpecialPower(mn);
			}
			else
			{
				cuser1.special_activation=true;
				playing.Hero_Magic=true;
			}
			
			mn.AttackHero(playing);
			this.FightMenu(arch1, cuser1, mymap1, mn, variable);
			//cuser1.GetUserHero().SpecialPower(mn);
		}
		else
		{
			cuser1.num_moves--;
			System.out.println("Invalid Option");
			this.FightMenu(arch1, cuser1, mymap1, mn,variable);
		}
		
	}
	void LevelUp(ArchLegends arch1,User cuser1, Map mymap1,Monster mn,int variable,Hero playing)
	{

		
		System.out.println("Level Up: Level: "+(cuser1.user_level+1));
		cuser1.user_level++;
		playing.Hero_AttackAttribute++;
		playing.Hero_DefenceAttribute++;
		
	//	this.MainAttack(arch1, cuser1, mymap1, mn, variable, playing);
		
	}
	
	void AddSideKick(ArchLegends arch1,User cuser1, Map mymap1,Monster mn,int variable,Hero playing)
	{
		System.out.println("Your current XP is: "+playing.GetHeroXP());
		System.out.println("If you want to buy a minion, press 1");
		System.out.println("If you want to buy a knight, press 2");
		Scanner d = new Scanner(System.in);
		int opt = d.nextInt();
		System.out.println("XP To Spend: ");
		int amount = d.nextInt();
		if(opt==1 && amount>=5)
		{
			playing.withSK=true;
			playing.SetHeroXP(playing.GetHeroXP()-opt);
			System.out.println("You bought a sidekick: minion");
			SideKick latest = new Minions();
			playing.AllSideKick.add(latest);
			System.out.println("XP Of Sidekick: "+latest.GetSXP());

//			System.out.println("PRICE        "+0.5*(amount-latest.cost));
//			System.out.println(latest.sidekick_attack);
			
			latest.sidekick_attack =latest.sidekick_attack + (0.5*(amount-latest.cost));
			System.out.println("Attack of sidekick is: "+latest.sidekick_attack);
			
		}
		else if(opt==2 && amount>=8)
		{
			playing.withSK=true;
			playing.SetHeroXP(playing.GetHeroXP()-opt);
			System.out.println("You bought a sidekick: knight");
			SideKick latest = new Knight();
			playing.AllSideKick.add(latest);
			System.out.println("XP Of Sidekick: "+latest.GetSXP());
			
//			System.out.println("PRICE        "+0.5*(amount-latest.cost));
//			System.out.println(latest.sidekick_attack);
			
			latest.sidekick_attack =latest.sidekick_attack + (0.5*(amount-latest.cost));
			System.out.println("Attack of sidekick is: "+latest.sidekick_attack);
			
			
		}
		else
		{
			System.out.println("Not enough XP");
		}
	}
	
	void SideMenu(ArchLegends arch1,User cuser1, Map mymap1,Monster mn,int variable,Hero playing)
	{
		System.out.println("If You would you like to buy a sidekick, type yes. Else type no to upgrade level");
		Scanner e = new Scanner(System.in);
		String g = e.next();
		
		if(g.equals("no"))
		{
			this.LevelUp(arch1,cuser1,mymap1,mn,variable,playing);
		}
		else if(g.equals("yes"))
		{
			this.AddSideKick(arch1, cuser1, mymap1, mn, variable, playing);
		}
		else
		{
			System.out.println("Invalid Option");
			this.SideMenu(arch1, cuser1, mymap1, mn, variable, playing);
		}
	}
}


class SideKick
{
	
	private double SXP;
	private double SHP;
	private final int MAXSHP = 100; 
	double sidekick_attack;
	boolean cloned;
	final int maxSH=100;
	protected String skname;
	double cost;
	ArrayList<SideKick> clone_side = new ArrayList<SideKick>();
	
	double sk_increased_xp;
	
	double GetSHP()
	{
		return SHP;
	}
	void SetSHP(double h)
	{
		SHP=h;
	}
	double GetSXP()
	{
		return SXP;
	}
	void SetSXP(double x)
	{
		double inc = this.GetSXP() - x;
		SXP=x;
		this.sk_increased_xp += inc;
		/*if(this.sk_increased_xp%5==0 )
		{
			this.sidekick_attack+=1;
		}*/
	}
	

	void clone(SideKick m)
	{
		clone_side.add(m);
		clone_side.add(m);
		clone_side.add(m);
	}
	

	public boolean equal(SideKick k)
	{
		return (this.GetSXP()==k.GetSXP());
	}
	public boolean Greater(SideKick k)
	{
		return (this.GetSXP()>k.GetSXP());
	}
	public boolean Smaller(SideKick k)
	{
		return (this.GetSXP()<k.GetSXP());
	}
	
	public static final Comparator<SideKick> ChooseSideKick = new Comparator<SideKick>()
	{
		@Override
		public int compare(SideKick fir,SideKick sec)
		{
			if(fir.Greater(sec))
			{
				return -1;
			}
			else if(fir.Smaller(sec))
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
	};
	
}

class Minions extends SideKick
{
	final int minion_cost = 5;
//	int clone_num = 1;
	
	Minions()
	{
		this.sidekick_attack = 1;
		this.SetSHP(100);
		this.SetSXP(0);
		this.cloned = false;
		this.skname = "minion";
		//this.maxSH = 100;
		this.cost = 5;
	}
	
	
}

class Knight extends SideKick
{
	final int knight_cost = 8;
	
	Knight()
	{
		this.sidekick_attack = 2;
		this.SetSHP(100);
		this.SetSXP(0);
		this.cloned = false;
		this.skname = "knight";
		this.cost = 8;
		
	}
	
	
}

class User
{
	final private String name;
	private Hero Current_Hero;
	int User_position;
	int num_moves;
	boolean user_magic;
	int user_level;
	boolean special_activation;
	int special_activation_count;
	
	
/*	User(String n,Hero h)
	{
		name=n;
		Current_Hero=h;
	}*/
	User(String n)
	{
		name=n;
		User_position=1;
		num_moves=0;
		user_level=1;
		special_activation=false;
		special_activation_count=0;
		
	//	Current_Hero=h;
	}
	
	String GetUserName()
	{
		return this.name;
	}
	Hero GetUserHero()
	{
		return this.Current_Hero;
	}
	void SetHero(Hero h)
	{
		//h.Attack(new Monster());
		Current_Hero=h;
	}
	
}

class ArchLegends
{
	protected ArrayList<User> All_Users = new ArrayList<User>();
	boolean game_start=false;
	
	ArchLegends()
	{
		game_start=true;
	}
	
	void GameMenu()
	{
		System.out.println("Choose Your Option");
		System.out.println("1) New User");
		System.out.println("2) Existing User");
		System.out.println("3) Exit");
		Scanner s = new Scanner(System.in);
		int option = s.nextInt();
		if(option==1)
		{
			System.out.println("Enter Username");
			String now = s.next();
			System.out.println("Choose a Hero");
			System.out.println("1) Warrior");
			System.out.println("2) Thief");
			System.out.println("3) Mage");
			System.out.println("4) Healer");
			int choose = s.nextInt();

			User CheckIn = new User(now);
			
		//	Hero h = new Hero();
			if(choose==1)
			{
				//h = (Warrior)h;
				//Warrior h=new Warrior();
				
				CheckIn.SetHero(new Warrior());
				CheckIn.GetUserHero().SetHeroName("Warrior");
				//System.out.println(h);
			}
			else if(choose==2)
			{
			//	Thief h=new Thief();
				CheckIn.SetHero(new Thief());
				CheckIn.GetUserHero().SetHeroName("Thief");
			}
			else if(choose==3)
			{
				//Mage h=new Mage();
				
				CheckIn.SetHero(new Mage());
				CheckIn.GetUserHero().SetHeroName("Mage");
			}
			else if(choose==4)
			{
				//Healer h=new Healer();

				CheckIn.SetHero(new Healer());
				CheckIn.GetUserHero().SetHeroName("Healer");
			}
			
		//	User CheckIn = new User(now,h);
			All_Users.add(CheckIn);
			System.out.println("User Creation Done. Username: "+CheckIn.GetUserName()+". Hero Type: "+CheckIn.GetUserHero().heroname);
			
			this.GameMenu();	
		}
		
		else if(option==2)
		{
			System.out.println("Enter Username");
			String whoplays = s.next();
			GameLayout mygame = new GameLayout();
			
			int check = FindUser(whoplays);
			if(check==-1)
			{
				System.out.println("User does not Exist");
				this.GameMenu();
			}
			else
			{
				System.out.println("User Found...Logging In");
				Map newmap = new Map();
				mygame.LocationMenu(this,All_Users.get(check),All_Users.get(check).User_position,newmap);
			}
			
		}
		else
		{
			System.exit(0);
		}
		
	}
	
	int FindUser(String n)
	{
		for(int i=0;i<this.All_Users.size();i++)
		{
			if((this.All_Users.get(i).GetUserName()).equals(n))
			{
				return i;
			}
		}
		return -1;
	}
}

public class sidekickmenu {

	public static void main(String[] args) 
	{
		ArchLegends mygame = new ArchLegends();
		mygame.GameMenu();

	}

}

