package com.twinklez.se.common;

import java.io.File;
import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

/*@Mod(modid = "SECoreMod", version = "0.4.2")
@IFMLLoadingPlugin.Name(value = "SECoreMod")*/

public class SEFMLLoadingPlugin implements IFMLLoadingPlugin, IFMLCallHook {
	
	public static boolean runtimeDeobfEnabled = false;
	public static File location;
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[] {"com.twinklez.se.common.SEClassTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return SEContainer.class.getName();
	}

	@Override
	public String getSetupClass() {
		return "com.twinklez.se.common.SEFMLLoadingPlugin";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		runtimeDeobfEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public Void call() throws Exception {
		return null;
	}

}
