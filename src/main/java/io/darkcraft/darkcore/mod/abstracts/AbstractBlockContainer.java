package io.darkcraft.darkcore.mod.abstracts;

import io.darkcraft.darkcore.mod.datastore.SimpleDoubleCoordStore;
import io.darkcraft.darkcore.mod.helpers.ServerHelper;
import io.darkcraft.darkcore.mod.helpers.WorldHelper;
import io.darkcraft.darkcore.mod.interfaces.IActivatable;
import io.darkcraft.darkcore.mod.interfaces.IBlockUpdateDetector;
import io.darkcraft.darkcore.mod.interfaces.IMultiBlockPart;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class AbstractBlockContainer extends AbstractBlock implements ITileEntityProvider
{
	private boolean	dropWithData	= false;

	public AbstractBlockContainer(String sm)
	{
		super(sm);
		this.isBlockContainer = true;
	}

	public AbstractBlockContainer(boolean render, String sm)
	{
		super(render, sm);
		this.isBlockContainer = true;
	}

	public AbstractBlockContainer(boolean visible, boolean _dropWithData, String sm)
	{
		super(visible, sm);
		this.isBlockContainer = true;
		dropWithData = _dropWithData;
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block par5, int par6)
	{
		TileEntity te = w.getTileEntity(x, y, z);
		if (te instanceof IMultiBlockPart)
			((IMultiBlockPart) te).recheckCore();
		super.breakBlock(w, x, y, z, par5, par6);
		w.removeTileEntity(x, y, z);
	}

	@Override
	public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
		TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
		return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer pl, int s, float i, float j, float k)
	{
		if(this instanceof IActivatable)
			if(((IActivatable)this).activate(pl, s))
				return true;
		TileEntity te = w.getTileEntity(x, y, z);
		if (te instanceof IActivatable)
			return ((IActivatable) te).activate(pl, s);
		return false;
	}

	@Override
	protected void dropBlockAsItem(World w, int x, int y, int z, ItemStack is)
	{
		//do not drop items while restoring blockstates, prevents item dupe
		if (ServerHelper.isServer() && w.getGameRules().getGameRuleBooleanValue("doTileDrops") && !w.restoringBlockSnapshots)
		{
			if (dropWithData)
			{
				TileEntity te = w.getTileEntity(x, y, z);
				if (te != null)
				{
					NBTTagCompound nbt;
					if (is.stackTagCompound != null)
						nbt = is.stackTagCompound;
					else
						nbt = new NBTTagCompound();
					te.writeToNBT(nbt);
					is.stackTagCompound = nbt;
				}
			}
			if (captureDrops.get())
			{
				capturedDrops.get().add(is);
				return;
			}
			float f = 0.7F;
			double d0 = w.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = w.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = w.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			WorldHelper.dropItemStack(is, new SimpleDoubleCoordStore(w,x+d0,y+d1,z+d2));
		}
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block neighbourBlockID)
	{
		TileEntity te = w.getTileEntity(x, y, z);
		if (te != null && te instanceof IBlockUpdateDetector)
		{
			((IBlockUpdateDetector) te).blockUpdated(neighbourBlockID);
		}
	}

	public abstract Class<? extends TileEntity> getTEClass();

}
