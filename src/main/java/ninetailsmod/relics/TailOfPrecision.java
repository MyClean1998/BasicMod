package ninetailsmod.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import ninetailsmod.cards.BaseCard;
import ninetailsmod.characters.NineTailsCharacter;

import static ninetailsmod.BasicMod.makeID;

public class TailOfPrecision extends BaseRelic {

    private static final String NAME = "TailOfPrecision"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity. TODO: make sure if doesn't show up elsewhere
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public TailOfPrecision() {
        super(ID, NAME, NineTailsCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], 4);
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        if (c.hasTag(BaseCard.CustomTags.CLAW))
            return damage + 4.0F;
        return damage;
    }

    public AbstractRelic makeCopy() {
        return new TailOfPrecision();
    }
}
