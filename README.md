## PART 1 (Installation)

* Download Eclipse Oxygen ([Eclipse IDE for Java Developers](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/oxygenr))
* Clone this repository in order to have access to the following files: `KAMP.launch, kamp_ruledsl_all_software_components.p2f, projectSet.psf`
* <kbd>Import -> Install Software Items from File -> Select File kamp_ruledsl_all_software_components.p2f</kbd>
* <kbd>Security Warning -> Install Anyway</kbd>
* <kbd> Popup appears -> Restart Eclipse Now</kbd>

After Eclipse restarts:

* **Change file encoding to UTF-8**: <kbd>Window -> Preferences -> General -> Workspace : Text file encoding</kbd>
* <kbd>Import -> Team -> Team Project Set -> Select File projectSet.psf</kbd>
* **Change Package Explorer View**: <kbd>Arrow down -> Top Level Elements -> Working Sets</kbd>
* Wait some time until all references are resolved and workspace build process finished - 
  you may watch the progress using the Progress view: <kbd>Window -> Show View -> Progress</kbd>
* **Trigger the mwe2 Workflow**: Navigate into KAMP-DSL/edu.kit.ipd.sdq.kamp.ruledsl/src/ -> *package* edu.kit.ipd.sdq.kamp.ruledsl,
 right click on <kbd>GenerateKampRuleLanguage.mwe2 -> Run as -> MWE2 Workflow</kbd> (ignore errors during launch because they will be resolved later);
    * **Open the console**: <kbd>Window -> Show view -> Console</kbd> and watch the progress
    * When it says 'Done.' we are... done!
 
 * **Import Launch Configuration** using <kbd>File -> Import -> Run/Debug -> Launch Configurations -> Select the File KAMP.launch -> Select all configs -> OK</kbd><p>Why? we provide a custom launch configuration in order to limit the bundles which are loaded at startup and thus elminating errors by these unnecessary bundles</p>
* <kbd>Run -> KAMP</kbd> (use the imported launch configuration)

 ## PART 2 (Create a simple project)

 #### Create the KAMP4BP Project
 * Click on <kbd>File -> New -> Other... -> Plugin-Project</kbd>
   * set 'Project name' e.g. 'MyKampProject' and then click <kbd>Next -> Finish</kbd>
    * Eclipse Confirmation Dialog may appear which asks if you want to change the perspective. I do not recommend to do it, but it basically does not matter.
    * click on project root and create folder with name 'modified'
    * **use this folder to insert all the following files**. That means, you must right click on this folder when creating files.
	
* **Create the Repository Model**: right click on the modified folder and select <kbd>New -> Other... -> Example EMP Model Creation Wizards -> Repository Model</kbd>
  * <kbd>Next -> You may change the filename -> Next -> Model Object: Repository</kbd>
	
* **Create the BPModificationmarks Model**: right click on modified folder and select <kbd>New -> Other... -> Example EMP Model Creation Wizards -> BPModificationmarks Model</kbd>
  * <kbd>Next (you may change the filename) -> Finish</kbd>
	
* **Create the entries in repository**, e.g.:
  * Double click My.repository (Repository Model Editor should open, if it does not do the following: rightclick on <kbd>My.repository -> Open With -> Other... -> Repository Model Editor -> OK</kbd>)
  * expand the topmost tree element (xxx/modified/My.repository)
  * right click item which states <kbd>'aName <Repository>' -> New Child -> Add OperationInterface as Interfaces</kbd>
  * right click on new <kbd>item -> New Child -> Add OperationSignature as Signatures</kbd>
  * open Properties (if not available do the following: <kbd>Window -> ShowView -> Other... -> General -> Properties</kbd>) and for each new item:
    * give a menaningful name such as: MyInterface for Interface's *Entity Name* property, getXXX for OperationSignature and so on...
	
  * right click item which states <kbd>'aName <Repository>' -> New Child -> Add BasicComponent as Components</kbd>
  * right click new <kbd>item -> New Child -> Add OperationProvidedRole as Provided Roles</kbd>
  * open Properties of the OperationProvidedRole
  * insert a meaningful Entity Name such as: MyComponent
  * click into the select box for 'Provided Interface Operation Provided Role' -> select 'Operation Interface MyInterface' (the only possibility)
	

* **Create the entries in modificationmarks**, e.g.:
  * open modificationmarks in Modificationmarks Model Editor (analogue to steps above)
	
  * first, we have to import the My.repository file into the ResourceSet: We do this by right clicking into the <kbd>Resource Set view -> Load Resource... -> Browse Workspace... -> modified -> OK -> OK</kbd>
	
  * furthermore expand the topmost tree element (xxx/modified/My.modificationmarks) and the subitem BP Modification Repository
  * right click <kbd>item 'BP Seed Modifications' -> New Child -> IS Modify Signature</kbd>
  * open Properties of the new item 'IS Modify Signature'
  * Click into the select box for 'Affected Element' and select 'Operation Signature getXXX' (the only possibility)
	
Now that you know how to create the models, I just tell you what to do for the next one not how to do it. You should be able to do it on your own now... (-- please note that this guide is written towards a total beginner audience, that is why this is mentioned here --)

* Create a new model 'usagemodel' with a 'Usage Scenario' as first child
  * Add the usagemodel into the ResourceSet of the My.modificationmarks file via Load Resource... etc. (see steps above)
  * Add a 'Scenario Behaviour' to the 'Usage Scenario' as direct child element
  * Add an 'Entry Level System Call' as child item of the 'Scenario Behaviour'
  * Give the 'Entry Level System Call' the following properties: Entity Name -> mainMethodCall, Operation Signature Entry Level System Call -> Operation Signature getXXX (the only possible option)
	
Now we have successfully set up a simple exemplary model.

#### Create the Rules
	
* Right click on MyKampProject -> Create Rule Definition File
* Double click on rules.karl (it is located inside the project root)
* If a dialog asks you to convert the project into anXtext project -> Yes
* Paste in the example contents from: [... link missing ...]
* Save the file
* Apply the quick fix which is proposed (Add all Vitruvius dependencies)
* Make some change to the file (e.g. add a comment) -> Save the file
* Now the MyKampProject-rules project should be built automatically
     * If an error occured, you can ignore it. It is a known bug (...reference missing...) that on setup something might go wrong
     * If you got an error but the project is created, you may  ensure everything is ok by making a change to the rules.karl file (such as adding a comment) -> Save the rules.karl file
      * Now a rebuilt is triggered which will fix all possible erros (such as missing dependencies)
      * The rebuild should finish without error!
    * Now you have successfully set up the rule creation
    * You can verify it by opening the My.modificationmarks file with the Modificationmarks Model Editor -> right click on BP Modification Repository -> Step 3: Calculate change propagation
    * Now all steps should be computed as expected
    * You will also get a dialog which says: 'Custom rule is working!'
    * Now you may go on writing more advanced KAMP rules!
	
## PART 3 (advanced- how to test the lookup and apply method)
    * you may want to test the lookup method: Open the file src.RuleProviderImpl.java and remove the custom rule which was added via override (i.e. remove the whole override method invokation with the sample anonymous class)
    * create a subclass of the TestRule called 'MyTestRule' inside the src package
    * override the apply method
    * call the lookup method inside your overriden apply method
    * you may output the result to System.out: please note that it written to the outer Eclipse console
    * you may also output the result via a message dialog, see sample call [... snippet url with the following contents...]

```java
final StringBuilder message = new StringBuilder();
message.append("We found the following elements:\n");
Display.getDefault().syncExec(new Runnable() {
    public void run() {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        MessageDialog.openInformation(shell, "Rule Lookup Result", message.toString());
    }
});
```
In order to call the method appropriately, you may use the following code:

```java
LookupUtil.lookupMarkedObjectsWithLookupMethod(version, OperationSignature.class, org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall.class, TestRule::lookupEntryLevelSystemCallfromOperationSignature)
    .forEach((result) -> {	
	System.out.println("Lookup retrieved: " + result.getElement().getEntityName());
    });
```
Do not forget to add the following inside the onRegisterReady method of your RuleProviderImpl class inside the source package: `override(new MyTestRule());`
	
-------------------------------------------------------	
TODO:
- explain how to deactivate the standard rules
- what happens if client code triggers an exception
- what are the current limititions and known bugs (e.g. one project per workspace)
- what exceptions might be thrown (e.g. for backreferences if kamp module does not implement the CrossReferenceProvider interface)
- some tricks such as displaying a MessageDialog (see snippet above)
- how to extend the generated rules (and how are the rules named... name+'Rule' classname) and limititions such as public constructor
    - one may implement a subclass or an anonymous class
- how to insert the lookups into the tree? (PART 3)

- mention a separate install config for Neon since Oxygen is the current version now and all others are deprecated!!
- mention: the team set references the Rule branches not master -> change this in future once the module is stable
