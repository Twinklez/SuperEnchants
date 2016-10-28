package com.twinklez.se.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;
/* Totally rewritten, using Forge ASM API 1.7 -- updated for 1.10.2. */

import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.IClassTransformer;

public class SEClassTransformer implements IClassTransformer {

	private static final HashMap<Integer, Integer> intCodes = new HashMap();

	/** Unobfuscated imported locations of classes in an array list. */
	protected static ArrayList<String> unobfList = new ArrayList(Arrays.asList(new String[] { 
			"net.minecraft.enchantment.EnchantmentArrowDamage", 
			"net.minecraft.enchantment.EnchantmentArrowFire", 
			"net.minecraft.enchantment.EnchantmentArrowInfinite", 
			"net.minecraft.enchantment.EnchantmentArrowKnockback", 
			"net.minecraft.enchantment.EnchantmentDamage", 
			"net.minecraft.enchantment.EnchantmentDurability", 
			"net.minecraft.enchantment.EnchantmentDigging", 
			"net.minecraft.enchantment.EnchantmentFireAspect", 
			"net.minecraft.enchantment.EnchantmentKnockback", 
			"net.minecraft.enchantment.EnchantmentLootBonus", 
			"net.minecraft.enchantment.EnchantmentOxygen", 
			"net.minecraft.enchantment.EnchantmentProtection",
			 "net.minecraft.enchantment.EnchantmentThorns", 
			"net.minecraft.enchantment.EnchantmentUntouching", 
			"net.minecraft.enchantment.EnchantmentWaterWorker"
		 }));	

	/** Obfuscated imported locations of classes in String ArrayList. default @ MC 1.10.2 only! 
	 *  Location of these obfuscated list names can be found in:   [note: ~ is your HOME, e.g. C:\Users\USERNAME]
	 *  ~\.gradle\caches\minecraft\de\oceanlabs\mcp\mcp_snapshot\<snapshotdate>\<mcversion>\srgs\notch-mcp.srg  
	 *  That location path is based on 1.9+ versions.
	 * */
	protected static ArrayList<String> obfList = new ArrayList(Arrays.asList(new String[] { 
			"ago", //arrowdamage
			"agp", //arrowfire
			"agq", //arrowinfinite
			"agr", //arrowknockback
			"ags", //damage
			"agt", //durability
			"agu", //digging
			"aha", //fireaspect
			"ahd", //knockback
			"ahe", //lootbonus
			"ahg", //oxygen
			"ahh", //protection
			"ahi", //thorns
			"ahj", //untouching
			"ahl" //waterworker
			}));
	
	/** ArrayList of Vanilla Levels [in order] */
	protected static int[] vanillaLevels = { 5, 1, 1, 2, 5, 3, 5, 2, 2, 3, 3, 4, 3, 1, 1 };
	
	/** ArrayList of SuperEnchant Levels [in order] */
	public static int[] seLevels = { 10, 1, 1, 5, 10, 5, 10, 5, 5, 5, 3, 10, 5, 1, 1 };
	
	/** Finds the classes that need to have bytecode insertion. */
	@Override
	public byte[] transform(String classname, String arg1, byte[] bytearray)
	{
		System.out.println(classname);
		int e_index = 0;
		if (obfList.contains(classname)) {
			e_index = obfList.indexOf(classname);
			System.out.println("Ready to transform: " + classname);
			return patchClassASM(classname, bytearray, true, e_index);
		}
		if (unobfList.contains(arg1))
		{
			e_index = unobfList.indexOf(arg1);
			System.out.println("Ready to transform: " + classname);
			return patchClassASM(arg1, bytearray, false, e_index);
		}
		return bytearray;
	}
	
	/** Patches the class, finds "getMaxLevel" or "b" when in an obfuscated environment. 
	 * 
	 * source (notch-mcp.srg) >> MD: ago/b ()I net/minecraft/enchantment/EnchantmentArrowDamage/getMaxLevel ()I
	 * ()I for 
	 * 
	 * 
	 * */
	public byte[] patchClassASM(String name, byte[] b, boolean obf, int e_index)
	{
		System.out.println("yo dawg");
		String methodName = "";
		if (obf == true) methodName = "b";
		else methodName = "getMaxLevel";
		
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(b);
		cr.accept(cn, 0);
		Iterator methods = cn.methods.iterator();

		while (methods.hasNext())
		{
			MethodNode mn = (MethodNode)methods.next();
			int fdiv_index = -1;
			
			/* Checking for methodName(b/getMaxLevel()), and verifying it by checking if it is an int[I], with no subtypes[()] 
			 * 
			 * For example if it were (I)I -- an example method would be the following:
			 * private int number(int i) {}
			 * 
			 * In this case it is the following:
			 * private int getMaxLevel() {}
			 * */
			if ((mn.name.equals(methodName)) && (mn.desc.equals("()I")))
			{
				AbstractInsnNode newinst;
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;
				Iterator it = mn.instructions.iterator();
				int index = -1;
			
				while (it.hasNext())
				{
					index++;
					currentNode = (AbstractInsnNode)it.next();
					if (currentNode.getOpcode() != ((Integer)intCodes.get(Integer.valueOf(vanillaLevels[e_index]))).intValue())
						continue;
					System.out.println("Bytecode Verified @ [" + currentNode.getOpcode() + "]");
					targetNode = currentNode;
					fdiv_index = index;
				}
				
				if (targetNode == null)
				{
					return b;
				}
				
				if (fdiv_index == -1)
				{
					return b;
				}
				
				AbstractInsnNode ourNode = mn.instructions.get(fdiv_index);
				
				if (vanillaLevels[e_index] == seLevels[e_index])
					break;
				System.out.println("Changing enchantment level capacities...");
				if (seLevels[e_index] > 5)
					newinst = new IntInsnNode(((Integer)intCodes.get(Integer.valueOf(seLevels[e_index]))).intValue(), seLevels[e_index]);
				else
					newinst = new InsnNode(((Integer)intCodes.get(Integer.valueOf(seLevels[e_index]))).intValue());
				
				mn.instructions.set(ourNode, newinst);
				System.out.println("Finished patching successfully!"); 
					break;
			}
		}
		
		ClassWriter writer = new ClassWriter(3);
		cn.accept(writer);
		return writer.toByteArray();
	}
	static {
	    intCodes.put(Integer.valueOf(1), Integer.valueOf(4));
	    intCodes.put(Integer.valueOf(2), Integer.valueOf(5));
	    intCodes.put(Integer.valueOf(3), Integer.valueOf(6));
	    intCodes.put(Integer.valueOf(4), Integer.valueOf(7));
	    intCodes.put(Integer.valueOf(5), Integer.valueOf(8));
	    intCodes.put(Integer.valueOf(6), Integer.valueOf(16));
	    intCodes.put(Integer.valueOf(7), Integer.valueOf(16));
	    intCodes.put(Integer.valueOf(8), Integer.valueOf(16));
	    intCodes.put(Integer.valueOf(9), Integer.valueOf(16));
	    intCodes.put(Integer.valueOf(10), Integer.valueOf(16));
	    int i;
	    for (i = 0; i < 11; i++) {
	    	System.out.println("IntCodes.PUT -> ID:" + i);
	    	System.out.println(Minecraft.getMinecraft().getVersion());
	    }
	}
}
