package io.darkcraft.darkcore.mod.nbt.impl;

import io.darkcraft.darkcore.mod.nbt.Mapper;
import io.darkcraft.darkcore.mod.nbt.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class BasicMappers
{

	public static void register()
	{
		NBTHelper.register(String.class, stringMapper);
		NBTHelper.register(Long.class, longMapper);
		NBTHelper.register(Double.class, doubleMapper);
		NBTHelper.register(Byte.class, byteMapper);
		NBTHelper.register(byte[].class, byteArrMapper);
		NBTHelper.register(Integer.class, intMapper);
		NBTHelper.register(int[].class, intArrMapper);
		NBTHelper.register(Boolean.class, boolMapper);
		NBTHelper.register(ItemStack.class, isMapper);
	}

	public static Mapper<String> stringMapper = new PrimMapper<String>()
	{
		@Override
		public void writeToNBT(NBTTagCompound nbt, String id, String t) { nbt.setString(id, t); }

		@Override
		public String readFromNBT(NBTTagCompound nbt, String id) { return nbt.getString(id); }
	};

	public static Mapper<Byte> byteMapper = new PrimMapper<Byte>()
	{
		@Override
		public void writeToNBT(NBTTagCompound nbt, String id, Byte t) { nbt.setByte(id, t); }

		@Override
		public Byte readFromNBT(NBTTagCompound nbt, String id) { return nbt.getByte(id); }
	};

	public static Mapper<byte[]> byteArrMapper = new PrimMapper<byte[]>()
	{
		@Override
		public void writeToNBT(NBTTagCompound nbt, String id, byte[] t) { nbt.setByteArray(id, t); }

		@Override
		public byte[] readFromNBT(NBTTagCompound nbt, String id) { return nbt.getByteArray(id); }
	};

	public static Mapper<Integer> intMapper = new PrimMapper<Integer>()
	{
		@Override
		public void writeToNBT(NBTTagCompound nbt, String id, Integer t) { nbt.setInteger(id, t); }

		@Override
		public Integer readFromNBT(NBTTagCompound nbt, String id) { return nbt.getInteger(id); }
	};

	public static Mapper<int[]> intArrMapper = new PrimMapper<int[]>()
	{
		@Override
		public void writeToNBT(NBTTagCompound nbt, String id, int[] t) { nbt.setIntArray(id, t); }

		@Override
		public int[] readFromNBT(NBTTagCompound nbt, String id) { return nbt.getIntArray(id); }
	};

	public static Mapper<Long> longMapper = new PrimMapper<Long>()
	{
		@Override
		public void writeToNBT(NBTTagCompound nbt, String id, Long t) { nbt.setLong(id, t); }

		@Override
		public Long readFromNBT(NBTTagCompound nbt, String id) { return nbt.getLong(id); }
	};

	public static Mapper<Double> doubleMapper = new PrimMapper<Double>()
	{
		@Override
		public void writeToNBT(NBTTagCompound nbt, String id, Double t) { nbt.setDouble(id, t); }

		@Override
		public Double readFromNBT(NBTTagCompound nbt, String id) { return nbt.getDouble(id); }
	};

	public static Mapper<Boolean> boolMapper = new PrimMapper<Boolean>()
	{
		@Override
		public void writeToNBT(NBTTagCompound nbt, String id, Boolean t) { nbt.setBoolean(id, t); }

		@Override
		public Boolean readFromNBT(NBTTagCompound nbt, String id) { return nbt.getBoolean(id); }
	};

	public static Mapper<ItemStack> isMapper = new Mapper<ItemStack>()
	{

		@Override
		public void writeToNBT(NBTTagCompound nbt, ItemStack o)
		{
			if(o == null)
				return;
			o.writeToNBT(nbt);
		}

		@Override
		public ItemStack fillFromNBT(NBTTagCompound nbt, ItemStack o)
		{
			if(o == null)
				return createFromNBT(nbt);
			ItemStack t = o;
			t.readFromNBT(nbt);
			return t;
		}

		@Override
		public ItemStack createFromNBT(NBTTagCompound nbt, Object... arguments)
		{
			return ItemStack.loadItemStackFromNBT(nbt);
		}
	};

	public static Mapper<FluidStack> fsMapper = new Mapper<FluidStack>()
	{

		@Override
		public void writeToNBT(NBTTagCompound nbt, FluidStack o)
		{
			if(o == null)
				return;
			o.writeToNBT(nbt);
		}

		@Override
		public FluidStack fillFromNBT(NBTTagCompound nbt, FluidStack t)
		{
			return FluidStack.loadFluidStackFromNBT(nbt);
		}

		@Override
		public FluidStack createFromNBT(NBTTagCompound nbt, Object... arguments)
		{
			return FluidStack.loadFluidStackFromNBT(nbt);
		}

		@Override
		public boolean shouldCreateNew() { return true; }
	};
}
