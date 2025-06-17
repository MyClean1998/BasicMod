package ninetailsmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import ninetailsmod.BasicMod;
import ninetailsmod.cards.skills.BaggedEscape;

public class BaggedEscapePower extends BasePower {
    public static final String ID = BasicMod.makeID(BaggedEscape.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"

    public BaggedEscapePower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, amount);
    }
}
