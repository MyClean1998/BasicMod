package ninetailsmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import ninetailsmod.BasicMod;

import java.lang.reflect.Field;

public class LoseIntentPower extends BasePower {

    public static final String ID = BasicMod.makeID(LoseIntentPower.class.getSimpleName());

    private byte moveByte;
    private AbstractMonster.Intent moveIntent;
    private EnemyMoveInfo move;

    public LoseIntentPower(AbstractCreature owner, int amount)
    {
        super(ID, PowerType.DEBUFF, true, owner, amount);
        img = ImageMaster.loadImage("images/stslib/powers/32/stun.png");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication()
    {
        // Dumb action to delay grabbing monster's intent until after it's actually set
        addToBot(new AbstractGameAction()
        {
            @Override
            public void update()
            {
                if (owner instanceof AbstractMonster) {
                    moveByte = ((AbstractMonster) owner).nextMove;
                    moveIntent = ((AbstractMonster) owner).intent;
                    Field f = null;
                    try {
                        f = AbstractMonster.class.getDeclaredField("move");
                        f.setAccessible(true);
                        move = (EnemyMoveInfo) f.get(owner);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    ((AbstractMonster)owner).setMove((byte)-1, AbstractMonster.Intent.UNKNOWN);
                    ((AbstractMonster)owner).createIntent();
                    addToTop(new TextAboveCreatureAction((owner), TextAboveCreatureAction.TextType.INTERRUPTED));
                    addToTop(new SetMoveAction(((AbstractMonster)owner), (byte)-1, AbstractMonster.Intent.UNKNOWN));
                }
                isDone = true;
            }
        });
    }

    @Override
    public void atEndOfRound()
    {
        if (amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        } else {
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public void onRemove()
    {
        if (owner instanceof AbstractMonster) {
            AbstractMonster m = (AbstractMonster)owner;
            if (move != null) {
                m.setMove(moveByte, moveIntent, move.baseDamage, move.multiplier, move.isMultiDamage);
            } else {
                m.setMove(moveByte, moveIntent);
            }
            m.createIntent();
            m.applyPowers();
        }
    }
}
