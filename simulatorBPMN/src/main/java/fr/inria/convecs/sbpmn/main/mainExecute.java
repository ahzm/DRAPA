package fr.inria.convecs.sbpmn.main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.inria.convecs.sbpmn.visualisation.CreateChart;
import fr.inria.convecs.sbpmn.common.BPMNResources;
import fr.inria.convecs.sbpmn.common.Resource;
import fr.inria.convecs.sbpmn.deploy.BPMNController;
import fr.inria.convecs.sbpmn.deploy.WorkloadController;
import fr.inria.convecs.sbpmn.executor.ExecuteRuntimeTasks;

public class mainExecute {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//1. Deployment
		BPMNController bpmn = new BPMNController("Shipment", "Shipment");
    	bpmn.bpmnDeployment();
    	
    	//2. Resources
//    	int period_limite = 60;
//		double minUsage = 0.7;
//		double maxUsage = 0.9;
//		int activeCost = 40;
//		int passiveCost = 10;
		
//		double minUsage = 0.65;
//		double maxUsage = 0.85;
//		int activeCost = 40;
//		int passiveCost = 40;
//		
//		ArrayList<Resource> initResources = new ArrayList<>();
//		initResources.add(new Resource("res01", "employee", 1, minUsage, maxUsage, period_limite));
//		initResources.add(new Resource("res02", "driver", 1, minUsage, maxUsage, period_limite));
//		initResources.add(new Resource("res03", "car", 1, minUsage, maxUsage, period_limite));
//		initResources.add(new Resource("res04", "drone", 1, minUsage, maxUsage, period_limite));
		
    	//2. Resources
    	int period_limite = 60;
		
    	double minUsage = 0.65;
		double maxUsage = 0.85;
		int activeCost = 40;
		int passiveCost = 40;
		
		ArrayList<Resource> initResources = new ArrayList<>();
		
		initResources.add(new Resource("res01", "employee", 1, minUsage, maxUsage, activeCost, passiveCost,period_limite));
		initResources.add(new Resource("res02", "driver", 1, minUsage, maxUsage, activeCost, passiveCost, period_limite));
		initResources.add(new Resource("res03", "car", 1, minUsage, maxUsage, activeCost, passiveCost, period_limite));
		initResources.add(new Resource("res04", "drone", 1, minUsage, maxUsage, activeCost, passiveCost, period_limite));

		BPMNResources allResources = new BPMNResources(initResources);
		
		
		/*
		 * 
		 * */
		int period = 60;
		CreateChart createChart;
		try {
			createChart = new CreateChart(bpmn.getBpmnProcess(), initResources, period);
			createChart.showResPercentChart();
			createChart.showNbrDuplicaResChart();
			createChart.showAETChart();
			createChart.showCostChart();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//2. Generate instances (workload)
    	int nbrInstance = 10; // nbr of instances
    	
//    	Map<String, Object> variables = new HashMap<>(); //Variables in BPMN models
//		variables.put("num", 10);
		
		int checkPeriod = 5000; //check period every 2s
		
//    	WorkloadController workload = new WorkloadController(bpmn, nbrInstance, variables, checkPeriod);
//    	workload.startWorkload();
    	// ------ end workload -----------
    	
    	//3. Define workload, and generate process instances
		Map<String, Object> variables = new HashMap<>(); //Variables in BPMN models
		variables.put("num", 10);
		bpmn.generateInstances(2, variables); //2 is the number of instances generated
		
    	// Executor
		//4. Execute the process
    	ExecuteRuntimeTasks tasksExecutor = new ExecuteRuntimeTasks(bpmn, allResources);
    	tasksExecutor.executor(variables);
    	//
	}

}
