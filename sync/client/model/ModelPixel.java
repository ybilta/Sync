package sync.client.model;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.MathHelper;

public class ModelPixel extends ModelBase
{
	public ModelRenderer pixel;
	
	public Random rand;

	public ModelPixel()
	{
		rand = new Random();
		
		textureWidth = 64;
		textureHeight = 32;

		pixel = new ModelRenderer(this, 0, 0);
		pixel.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
		pixel.setRotationPoint(0F, 0F, 0F);
		pixel.setTextureSize(64, 32);
		pixel.mirror = true;
		setRotation(pixel, 0F, 0F, 0F);
	}

	public void renderPlayer(int prog, float renderYaw, float rotationYaw, float rotationPitch, float limbSwing, float limbSwingAmount, float f5, float renderTick)
	{
		GL11.glPushMatrix();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glColor4f(0.7F, 0.7F, 0.7F, 0.8F);
		
        GL11.glTranslatef(0.0F, -0.7F, 0.0F);
        
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		
        for(int i = 0; i < 6; i++)
        {
        	renderLimb(prog, i, renderYaw, rotationYaw, rotationPitch, limbSwing, limbSwingAmount, f5, renderTick);
        }
        
        GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPopMatrix();
	}
	
	public void renderLimb(int progInt, int limb, float renderYaw, float rotationYaw, float rotationPitch, float limbSwing, float limbSwingAmount, float f5, float renderTick)
	{
		GL11.glPushMatrix();
		//0 = left leg
		//1 = right leg
		//2 = left arm
		//3 = right arm
		//4 = body
		//5 = head
		
		float prog = MathHelper.clamp_float((float)(progInt + renderTick) / 100F, 0.0F, 1.0F);
		
		float shatterProg = MathHelper.clamp_float((float)(Math.pow(1.0F - MathHelper.clamp_float(((prog - 0.05F) / 0.125F), 0.0F, 1.0F), 3D)), 0.0F, 1.0F);
		float properShatterProg = 1.0F - MathHelper.clamp_float((float)(Math.pow(1.0F - MathHelper.clamp_float(((prog - 0.025F) / 0.2F), 0.0F, 1.0F), 2D)), 0.0F, 1.0F);
		
		rotationPitch *= shatterProg;
		
		if(limb != 5)
		{
			GL11.glRotatef(renderYaw, 0.0F, 1.0F, 0.0F);
		}
		else
		{
			GL11.glRotatef(rotationYaw, 0.0F, 1.0F, 0.0F);
		}

		
        float f7 = limbSwingAmount;
        float f8 = limbSwing - limbSwingAmount;
        
//        this.bipedRightLeg.rotateAngleX = Math.toDegrees(MathHelper.cos(f8 * 0.6662F) * 1.4F * f7);
//        this.bipedLeftLeg.rotateAngleX = Math.toDegrees(MathHelper.cos(f8 * 0.6662F + (float)Math.PI) * 1.4F * f7);

		
        int sizeX = 4;
        int sizeY = 12;
        int sizeZ = 4;
        
        float offsetX = 0.0F;
        float offsetY = 0.0F;
        float offsetZ = 0.0F;
        
		pixel.rotationPointX = 0;
		pixel.rotationPointY = 0;
		pixel.rotationPointZ = 0;
		
		float spread = 0.7F;
		
		if(limb == 0)
		{
			GL11.glTranslatef(2F/16F + ((rand.nextFloat() - 0.5F) * spread * properShatterProg), 0.0F, 0.0F);
		}
		else if(limb == 1)
        {
			GL11.glTranslatef(-2F/16F + ((rand.nextFloat() - 0.5F) * spread * properShatterProg), 0.0F, 0.0F);
        }
		else if(limb == 2)
        {
			GL11.glTranslatef(4F/16F + ((rand.nextFloat() - 0.5F) * spread * properShatterProg), -10F/16F * shatterProg, 0.0F);
			pixel.rotationPointX = 2F;
			pixel.rotationPointY = -2F * shatterProg;
        }
		else if(limb == 3)
        {
			GL11.glTranslatef(-4F/16F + ((rand.nextFloat() - 0.5F) * spread * properShatterProg), -10F/16F * shatterProg, 0.0F);
			pixel.rotationPointX = -2F;
			pixel.rotationPointY = -2F * shatterProg;
        }
		else if(limb == 4)
        {
        	sizeX = 8;
        	GL11.glTranslatef(0.0F, -12F/16F * shatterProg, 0.0F);
        }
        else if(limb == 5)
        {
        	sizeX = 8;
        	sizeY = 8;
        	sizeZ = 8;
        	GL11.glTranslatef(0.0F, -12F/16F * shatterProg, 0.0F);
        	
        	GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);
        	pixel.rotationPointY = -8F * shatterProg;
        }
		
		if(limb < 4)
		{
			if(limb % 2 == 0)
			{
				GL11.glRotated((Math.toDegrees(Math.cos(f8 * 0.6662F + (float)Math.PI) * 1.4F * f7)) * shatterProg, 1.0F, 0.0F, 0.0F);
			}
			if(limb % 2 == 1)
			{
				GL11.glRotated((Math.toDegrees(Math.cos(f8 * 0.6662F) * 1.4F * f7)) * shatterProg, 1.0F, 0.0F, 0.0F);
			}
		}
	        
		GL11.glTranslatef(0.0F, 11F/16F * (1.0F - shatterProg), 0.0F);
		
		float disappearProg = MathHelper.clamp_float((prog - 0.5F) / 0.5F, 0.0F, 1.0F);
		
        for(int i = -(sizeX / 2); i < (sizeX / 2); i++)
        {
            for(int j = -sizeY; j < 0; j++)
            {
                for(int k = -(sizeZ / 2); k < (sizeZ / 2); k++)
                {
                	if(i == -(sizeX / 2) || i == (sizeX / 2) - 1 || j == -sizeY || j == -1 || k == -(sizeZ / 2) || k == (sizeZ / 2) - 1)
                	{
                		GL11.glPushMatrix();
                		GL11.glTranslatef(-(offsetX + (i + 0.5F)) / 16F + ((rand.nextFloat() - 0.5F) * spread * properShatterProg), (-(offsetY + (j + 0.5F)) / 16F) * shatterProg, -(offsetZ + (k + 0.5F)) / 16F + ((rand.nextFloat() - 0.5F) * spread * properShatterProg));
                		if(prog < rand.nextFloat())
                		{

                			pixel.render(f5);
                		}
                		GL11.glPopMatrix();
                	}
                }
            }
        }
	    GL11.glPopMatrix();
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
