package ninetailsmod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import ninetailsmod.characters.NineTailsCharacter;

import static ninetailsmod.BasicMod.makeID;

public class NineTailsRelic extends BaseRelic {

    private static final String NAME = "NineTailsRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private static final int COUNT = 9;

    public NineTailsRelic() {
        super(ID, NAME, NineTailsCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = COUNT;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], 1);
    }

    public void onTrigger() {
        flash();
        addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, this));
        int healAmt = AbstractDungeon.player.maxHealth;
        if (healAmt < 1)
            healAmt = 1;
        AbstractDungeon.player.heal(healAmt, true);
        this.counter--;
    }

    public AbstractRelic makeCopy() {
        return new NineTailsRelic();
    }

}
