package ninetailsmod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import ninetailsmod.characters.NineTailsCharacter;

import static ninetailsmod.BasicMod.makeID;

public class TailOfAgility extends BaseRelic {

    private static final String NAME = "TailOfAgility"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity. TODO: make sure if doesn't show up elsewhere
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public TailOfAgility() {
        super(ID, NAME, NineTailsCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], 1);
    }

    @Override
    public void atBattleStart() {
        this.addOneDexterity();
    }

    @Override
    public void onEquip() {
        this.addOneDexterity(); // Take effect immediately
    }

    public AbstractRelic makeCopy() {
        return new TailOfAgility();
    }

    private void addOneDexterity() {
        flash();
        addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new DexterityPower((AbstractCreature)AbstractDungeon.player, 1), 1));
        addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, this));
    }
}
