package ghidra.app.plugin.core.osgi;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleRequirement;

import generic.jar.ResourceFile;

public interface GhidraBundle {

	enum Type {
		BndScript, Jar, SourceDir, INVALID
	}

	/**
	 * clean build artifacts generated during build of this bundle
	 * 
	 * @return true if anything was done
	 */
	boolean clean();

	/**
	 * attempt to build loadable bundle, if possible
	 *  
	 * @param writer console for user messages 
	 * @return true if build was successful
	 * @throws Exception XXX
	 */
	boolean build(PrintWriter writer) throws Exception;

	String getBundleLoc();

	Bundle getBundle() throws GhidraBundleException;

	Bundle install() throws GhidraBundleException;

	String getSummary();

	static GhidraBundle.Type getType(ResourceFile rf) {
		if (rf.isDirectory()) {
			return GhidraBundle.Type.SourceDir;
		}
		String n = rf.getName().toLowerCase();
		if (n.endsWith(".bnd")) {
			return GhidraBundle.Type.BndScript;
		}
		if (n.endsWith(".jar")) {
			return GhidraBundle.Type.Jar;
		}
		return GhidraBundle.Type.INVALID;
	}

	static public GhidraBundle.Type getType(File f) {
		if (f.isDirectory()) {
			return GhidraBundle.Type.SourceDir;
		}
		String n = f.getName().toLowerCase();
		if (n.endsWith(".bnd")) {
			return GhidraBundle.Type.BndScript;
		}
		if (n.endsWith(".jar")) {
			return GhidraBundle.Type.Jar;
		}
		return GhidraBundle.Type.INVALID;
	}

	List<BundleRequirement> getAllReqs();

}