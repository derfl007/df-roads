package derfl007.roads.common.items;

import java.text.DecimalFormat;
import java.util.List;

import derfl007.roads.Roads;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemColorCartridge extends Item {
    public ItemColorCartridge(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(Roads.ROADS_TAB);
        this.setMaxDamage(32);
        this.maxStackSize = 1;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        float currentDamage = 32 - stack.getItemDamage();
        float maxDamage = stack.getMaxDamage();
        tooltip.add("Ink left: " + (decimalFormat.format((currentDamage/maxDamage) * 100) + "%"));
    }
}
