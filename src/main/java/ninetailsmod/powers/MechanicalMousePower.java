package ninetailsmod.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ninetailsmod.BasicMod;

import static ninetailsmod.util.ToyCardUtils.getToyCard;

public class MechanicalMousePower extends BasePower {
    public static final String ID = BasicMod.makeID(MechanicalMousePower.class.getSimpleName());

    public MechanicalMousePower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (int i = 0; i < this.amount; i++)
                addToBot(new MakeTempCardInHandAction(getToyCard().makeCopy(), 1, false));
        }
    }
}
