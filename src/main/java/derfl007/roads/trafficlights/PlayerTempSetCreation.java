package derfl007.roads.trafficlights;

@SuppressWarnings("serial")
public class PlayerTempSetCreation extends LightsSet {

	public String tempSetName;

	public boolean replacing;
	public boolean editing;
	
	public int selectedGroupIndex;

	public PlayerTempSetCreation(String tempName) {
		tempSetName = tempName;
	}

	public PlayerTempSetCreation(String tempName, LightsSet baseSet) {
		tempSetName = tempName;
		if (baseSet == null) {
			return;
		}
		for (LightsGroup group : baseSet) {
			this.add(new LightsGroup(group));
		}
		editing = true;
	}

}
