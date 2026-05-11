package com.industrium.core.api.network;

import com.industrium.core.api.power.VoltageTier;

/**
 * Specialized node for power networks.
 */
public interface IPowerNode extends IIndustriumNode {
    VoltageTier getTier();
}
