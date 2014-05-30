package com.acme.corp.tracker.subsystem.deployment;

import java.util.Iterator;
import java.util.List;

import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.Phase;
import org.jboss.as.server.deployment.module.ResourceRoot;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceRegistry;
import org.jboss.msc.service.StartContext;
import org.jboss.vfs.VirtualFile;

import com.acme.corp.tracker.extension.TrackerService;

/**
 * An example deployment unit processor that does nothing. To add more deployment
 * processors copy this class, and add to the {@link AbstractDeploymentChainStep}
 * {@link SubsystemAdd#performBoottime(org.jboss.as.controller.OperationContext, org.jboss.dmr.ModelNode, org.jboss.dmr.ModelNode, org.jboss.as.controller.ServiceVerificationHandler, java.util.List)}
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
public class SubsystemDeploymentProcessor implements DeploymentUnitProcessor {

    Logger log = Logger.getLogger(SubsystemDeploymentProcessor.class);

    /**
     * See {@link Phase} for a description of the different phases
     */
    public static final Phase PHASE = Phase.DEPENDENCIES;

    /**
     * The relative order of this processor within the {@link #PHASE}.
     * The current number is large enough for it to happen after all
     * the standard deployment unit processors that come with JBoss AS.
     */
    public static final int PRIORITY = 0x4000;

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        String name = phaseContext.getDeploymentUnit().getName();
        System.out.println("Comenzando deploy");
        List lista  = phaseContext.getServiceRegistry().getServiceNames();
    	Iterator iter = lista.iterator();
    	ModelNode modelNode=null;
    	while (iter.hasNext()) {
			ServiceName sn = (ServiceName) iter.next();
			 CharSequence cs = "jboss.deployment.unit";
			modelNode = phaseContext.getDeploymentUnit().getDeploymentSubsystemModel("com.acme.corp.tracker");
			ServiceController serviceController =phaseContext.getDeploymentUnit().getServiceRegistry().getService(sn);
		//	if (serviceController.getName().getCanonicalName().contains(cs)) {
				System.out.println(serviceController.getState()+" *** Nombre: "+ serviceController.getName());
				Service service = serviceController.getService();
				System.out.println(service.getClass().getName());
			//	System.out.println("Valor......."+service.getValue());
		//	}
			
			
			
			
//			System.out.println("Canonical: "+sn.getCanonicalName());
//			System.out.println("Simple: "+sn.getSimpleName());
//			
//			System.out.println("-------------------------");
			
		}
    	
    	
    	System.out.println(modelNode);
    	System.out.println("Fin deploy");
        ResourceRoot root = phaseContext.getDeploymentUnit().getAttachment(Attachments.DEPLOYMENT_ROOT);
        TrackerService service = getTrackerService(phaseContext.getServiceRegistry(), name);
        if (service != null) {
            VirtualFile cool = root.getRoot().getChild("META-INF/cool.txt");
            service.addDeployment(name);
            if (cool.exists()) {
                service.addCoolDeployment(name);
            }
        }
    }

    @Override
    public void undeploy(DeploymentUnit context) {
    	List lista  = context.getServiceRegistry().getServiceNames();
    	Iterator iter = lista.iterator();
    	System.out.println("Comenzando undeploy");
    	while (iter.hasNext()) {
			ServiceName sn = (ServiceName) iter.next();
			System.out.println("Canonical: "+sn.getCanonicalName());
			System.out.println("Simple: "+sn.getSimpleName());
			System.out.println("-------------------------");
			
		}
    	
        context.getServiceRegistry();
        String name = context.getName();
        TrackerService service = getTrackerService(context.getServiceRegistry(), name);
        if (service != null) {
            service.removeDeployment(name);
        }
    }

    private TrackerService getTrackerService(ServiceRegistry registry, String name) {
        int last = name.lastIndexOf(".");
        String suffix = name.substring(last + 1);
        ServiceController<?> container = registry.getService(TrackerService.createServiceName(suffix));
        if (container != null) {
            TrackerService service = (TrackerService)container.getValue();
            return service;
        }
        return null;
    }
}