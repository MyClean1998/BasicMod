package ninetailsmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import ninetailsmod.BasicMod;

public class BaggedEscapePower extends BasePower {
    public static final String ID = BasicMod.makeID(BaggedEscapePower.class.getSimpleName());
    private static final int REDUCED_DAMAGE = 5;

    public BaggedEscapePower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, amount);
    }

    public void updateDescription() {
        if (this.amount <= 1) {
            this.description = DESCRIPTIONS[0] + REDUCED_DAMAGE;
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + REDUCED_DAMAGE;
        }
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount <= REDUCED_DAMAGE) {
            return damageAmount;
        }
        addToTop(new ReducePowerAction(this.owner, this.owner, ID, 1));
        return REDUCED_DAMAGE;
    }
}
