package gaia.renderer.entity.layers;

import gaia.GaiaReference;
import gaia.entity.monster.EntityGaiaGorgon;
import gaia.model.ModelGaiaGorgon;
import gaia.renderer.entity.RenderGaiaGorgon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @see LayerWitherAura
 */
@SideOnly(Side.CLIENT)
public class LayerAuraGorgon implements LayerRenderer<EntityGaiaGorgon> {
	private static final ResourceLocation ARMOR = new ResourceLocation(GaiaReference.MOD_ID, "textures/entity/layer/aura_immune_ranged.png");
	private final RenderGaiaGorgon entityRenderer;
	private final ModelGaiaGorgon entityModel = new ModelGaiaGorgon(0.5F);

	public LayerAuraGorgon(RenderGaiaGorgon entityRendererIn) {
		this.entityRenderer = entityRendererIn;
	}

	public void doRenderLayer(EntityGaiaGorgon entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (entitylivingbaseIn.isArmored()) {
			GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
			this.entityRenderer.bindTexture(ARMOR);
			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();
			float f = (float) entitylivingbaseIn.ticksExisted + partialTicks;
			float f1 = MathHelper.cos(f * 0.02F) * 3.0F;
			float f2 = f * 0.01F;
			GlStateManager.translate(f1, f2, 0.0F);
			GlStateManager.matrixMode(5888);
			GlStateManager.enableBlend();
			float f3 = 0.5F;
			GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
			GlStateManager.disableLighting();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			this.entityModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
			this.entityModel.setModelAttributes(this.entityRenderer.getMainModel());
			Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
			this.entityModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();
			GlStateManager.matrixMode(5888);
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}