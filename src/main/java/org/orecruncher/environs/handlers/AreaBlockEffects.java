/*
 *  Dynamic Surroundings: Environs
 *  Copyright (C) 2020  OreCruncher
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package org.orecruncher.environs.handlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import org.orecruncher.environs.scanner.*;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class AreaBlockEffects extends HandlerBase {

    protected final ClientPlayerLocus locus = new ClientPlayerLocus();
    protected final RandomBlockEffectScanner nearEffects =
            new RandomBlockEffectScanner(
                this.locus,
                RandomBlockEffectScanner.NEAR_RANGE);
    protected final RandomBlockEffectScanner farEffects =
            new RandomBlockEffectScanner(
                this.locus,
                RandomBlockEffectScanner.FAR_RANGE);
    protected final AlwaysOnBlockEffectScanner alwaysOn =
            new AlwaysOnBlockEffectScanner(
                this.locus,
                org.orecruncher.sndctrl.Config.CLIENT.effects.get_effectRange());

    protected final BiomeScanner biomeScanner = new BiomeScanner();
    protected final CeilingCoverage ceilingCoverage = new CeilingCoverage();

    public AreaBlockEffects() {
        super("Area Block Effects");
    }

    @Override
    public void process(@Nonnull final PlayerEntity player) {
        this.ceilingCoverage.tick();
        this.biomeScanner.tick();
        this.nearEffects.tick();
        this.farEffects.tick();
        this.alwaysOn.tick();
    }

    @Override
    public void onConnect() {
        MinecraftForge.EVENT_BUS.register(this.alwaysOn);
    }

    @Override
    public void onDisconnect() {
        MinecraftForge.EVENT_BUS.unregister(this.alwaysOn);
    }
}
