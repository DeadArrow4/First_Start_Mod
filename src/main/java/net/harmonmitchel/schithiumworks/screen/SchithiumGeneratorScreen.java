package net.harmonmitchel.schithiumworks.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.harmonmitchel.schithiumworks.SCHITHIUMWORKS;
import net.harmonmitchel.schithiumworks.screen.renderer.EnergyInfoArea;
import net.harmonmitchel.schithiumworks.util.MouseUtil;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class SchithiumGeneratorScreen extends AbstractContainerScreen<SchithiumGeneratorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(SCHITHIUMWORKS.MOD_ID,"textures/gui/schithium_generator_gui.png");

    private EnergyInfoArea energyInfoArea;

    public SchithiumGeneratorScreen(SchithiumGeneratorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height -imageHeight) /2;
        energyInfoArea = new EnergyInfoArea(x + 163,y + 16,menu.blockEntity.getEnergyStorage()); //top pixel for energy bar


    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height -imageHeight) /2;

        renderEnergyAreaTooltips(pPoseStack,pMouseX,pMouseY, x, y);
    }

    private void renderEnergyAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY,int x,int y) {
        if(isMouseAboveArea(pMouseX,pMouseY,x,y,163,16,5,47)){
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(), Optional.empty(),pMouseX-x,pMouseY-y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX,pMouseY,x + offsetX,y + offsetY, width, height);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height -imageHeight) /2;
        this.blit(pPoseStack, x, y, 0,0,imageWidth,imageHeight);
        renderProgressArrow(pPoseStack, x, y);
        energyInfoArea.draw(pPoseStack);

    }
    private void renderProgressArrow(PoseStack pPoseStack,int x,int y){ //renders Progress
        if(menu.isCrafting()){
            blit(pPoseStack,x + 105, y + 33,176,0,8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack,mouseX,mouseY);
    }
}
