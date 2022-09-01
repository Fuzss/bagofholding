package fuzs.bagofholding.client.core;

import fuzs.puzzleslib.util.PuzzlesUtil;

public class ModClientServices {
    public static final ClientAbstractions CLIENT_ABSTRACTIONS = PuzzlesUtil.loadServiceProvider(ClientAbstractions.class);
}
