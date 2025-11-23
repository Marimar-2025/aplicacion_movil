import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, Alert, TextInput, Modal, ActivityIndicator, ScrollView } from 'react-native';
import { Picker } from '@react-native-picker/picker';
import { productsStyles } from '../styles/ProductsStyles';
import { productService, categoryService, subcategoryService, authService } from '../services/api';

export default function ProductsScreen() {
  const [products, setProducts] = useState<any[]>([]);
  const [categories, setCategories] = useState<any[]>([]);
  const [subcategories, setSubcategories] = useState<any[]>([]);
  const [filteredSubcategories, setFilteredSubcategories] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [editing, setEditing] = useState<any>(null);
  const [currentUser, setCurrentUser] = useState<any>(null);
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    price: '',
    stock: '',
    categoryId: '',
    subcategoryId: '',
    active: true
  });

  useEffect(() => {
    loadProducts();
    loadCategories();
    loadSubcategories();
    loadCurrentUser();
  }, []);

  useEffect(() => {
    if (formData.categoryId) {
      const filtered = (subcategories || []).filter(
        (sub) => sub?.category?.id?.toString() === formData.categoryId
      );
      setFilteredSubcategories(filtered || []);
    } else {
      setFilteredSubcategories([]);
    }
  }, [formData.categoryId, subcategories]);

  const loadCurrentUser = async () => {
    try {
      const user = await authService.getCurrentUser();
      setCurrentUser(user);
    } catch (error) {
      console.error('Error loading current user:', error);
    }
  };

  const loadProducts = async () => {
    setLoading(true);
    try {
      const response = await productService.getAll();
      setProducts(response?.data || []);
    } catch (error) {
      console.error('Error loading products:', error);
      setProducts([]);
      Alert.alert('Error', 'No se pudieron cargar los productos');
    } finally {
      setLoading(false);
    }
  };

  const loadCategories = async () => {
    try {
      const response = await categoryService.getAll();
      setCategories(response?.data || []);
    } catch (error) {
      console.error('Error loading categories:', error);
      setCategories([]);
    }
  };

  const loadSubcategories = async () => {
    try {
      const response = await subcategoryService.getAll();
      setSubcategories(response?.data || []);
    } catch (error) {
      console.error('Error loading subcategories:', error);
      setSubcategories([]);
    }
  };

  const handleSave = async () => {
    if (!formData.categoryId || !formData.subcategoryId) {
      Alert.alert('Error', 'Debe seleccionar categoría y subcategoría');
      return;
    }

    try {
      const data = {
        name: formData.name,
        description: formData.description,
        price: parseFloat(formData.price),
        stock: parseInt(formData.stock),
        active: formData.active,
        category: { id: parseInt(formData.categoryId) },
        subcategory: { id: parseInt(formData.subcategoryId) }
      };

      if (editing) {
        await productService.update(editing.id, data);
        Alert.alert('Éxito', 'Producto actualizado');
      } else {
        await productService.create(data);
        Alert.alert('Éxito', 'Producto creado');
      }
      setModalVisible(false);
      resetForm();
      loadProducts();
    } catch (error: any) {
      Alert.alert('Error', error.response?.data?.message || 'Error al guardar');
    }
  };

  const handleDelete = (item: any) => {
    if (currentUser?.role !== 'ADMIN') {
      Alert.alert('Acceso Denegado', 'Solo los administradores pueden eliminar');
      return;
    }

    Alert.alert('Confirmar', `¿Eliminar producto ${item.name}?`, [
      { text: 'Cancelar', style: 'cancel' },
      {
        text: 'Eliminar',
        style: 'destructive',
        onPress: async () => {
          try {
            await productService.delete(item.id);
            Alert.alert('Éxito', 'Producto eliminado');
            loadProducts();
          } catch (error) {
            Alert.alert('Error', 'No se pudo eliminar');
          }
        },
      },
    ]);
  };

  const handleToggleActive = (item: any) => {
    const action = item.active ? 'desactivar' : 'activar';
    Alert.alert('Confirmar', `¿${action.charAt(0).toUpperCase() + action.slice(1)} producto ${item.name}?`, [
      { text: 'Cancelar', style: 'cancel' },
      {
        text: action.charAt(0).toUpperCase() + action.slice(1),
        onPress: async () => {
          try {
            await productService.update(item.id, {
              name: item.name,
              description: item.description,
              price: item.price,
              stock: item.stock,
              active: !item.active,
              category: { id: item.category?.id },
              subcategory: { id: item.subcategory?.id }
            });
            Alert.alert('Éxito', `Producto ${item.active ? 'desactivado' : 'activado'}`);
            loadProducts();
          } catch (error) {
            Alert.alert('Error', `No se pudo ${action}`);
          }
        }
      }
    ]);
  };

  const openModal = (item: any = null) => {
    if (item) {
      setEditing(item);
      setFormData({
        name: item.name,
        description: item.description,
        price: item.price?.toString() || '',
        stock: item.stock?.toString() || '',
        categoryId: item.category?.id?.toString() || '',
        subcategoryId: item.subcategory?.id?.toString() || '',
        active: item.active
      });
    } else {
      resetForm();
    }
    setModalVisible(true);
  };

  const resetForm = () => {
    setEditing(null);
    setFormData({
      name: '',
      description: '',
      price: '',
      stock: '',
      categoryId: '',
      subcategoryId: '',
      active: true
    });
  };

  return (
    <View style={productsStyles.container}>
      {/* Header */}
      <View style={productsStyles.header}>
        <Text style={productsStyles.headerTitle}>Gestión de Productos</Text>
        <Text style={productsStyles.headerSubtitle}>
          Administra el inventario de productos
        </Text>
      </View>

      {/* Actions */}
      <View style={productsStyles.actionsContainer}>
        <TouchableOpacity
          style={productsStyles.primaryButton}
          onPress={() => openModal()}
        >
          <Text style={productsStyles.primaryButtonText}>+ Nuevo Producto</Text>
        </TouchableOpacity>
      </View>

      {/* Products List */}
      <FlatList
        data={products || []}
        keyExtractor={(item) => item?.id?.toString() || Math.random().toString()}
        refreshing={loading}
        onRefresh={loadProducts}
        style={productsStyles.list}
        contentContainerStyle={productsStyles.listContent}
        showsVerticalScrollIndicator={false}
        renderItem={({ item }) => {
          if (!item) return null;
          
          return (
            <View style={productsStyles.card}>
              <View style={productsStyles.cardContent}>
                <Text style={productsStyles.cardTitle}>
                  {item?.name || 'Sin nombre'} {!item.active && <Text style={{ color: '#999' }}>(Inactivo)</Text>}
                </Text>
                <Text style={productsStyles.cardSubtitle}>{item?.description || 'Sin descripción'}</Text>
                <Text style={productsStyles.cardPrice}>Precio: ${item?.price || '0'}</Text>
                <Text style={productsStyles.cardMeta}>Stock: {item?.stock || '0'}</Text>
                <Text style={productsStyles.cardMeta}>
                  {item?.category?.name || 'Sin categoría'} / {item?.subcategory?.name || 'Sin subcategoría'}
                </Text>
              </View>
              <View style={productsStyles.cardActions}>
                <TouchableOpacity
                  style={[productsStyles.actionButton, productsStyles.editButton]}
                  onPress={() => openModal(item)}
                >
                  <Text style={productsStyles.editButtonText}>Editar</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  style={[productsStyles.actionButton, item.active ? productsStyles.deleteButton : productsStyles.editButton]}
                  onPress={() => handleToggleActive(item)}
                >
                  <Text style={[item.active ? productsStyles.deleteButtonText : productsStyles.editButtonText]}>
                    {item.active ? 'Desactivar' : 'Activar'}
                  </Text>
                </TouchableOpacity>
                {currentUser?.role === 'ADMIN' && (
                  <TouchableOpacity
                    style={[productsStyles.actionButton, productsStyles.deleteButton]}
                    onPress={() => handleDelete(item)}
                  >
                    <Text style={productsStyles.deleteButtonText}>Eliminar</Text>
                  </TouchableOpacity>
                )}
              </View>
            </View>
          );
        }}
        ListEmptyComponent={
          !loading ? (
            <View style={productsStyles.emptyContainer}>
              <Text style={productsStyles.emptyText}>No hay productos registrados</Text>
              <Text style={productsStyles.emptySubtext}>
                Toca el botón "Nuevo Producto" para comenzar
              </Text>
            </View>
          ) : null
        }
      />

      <Modal visible={modalVisible} animationType="slide" transparent>
        <View style={productsStyles.modalOverlay}>
          <View style={productsStyles.modalContent}>
            <Text style={productsStyles.modalTitle}>{editing ? 'Editar Producto' : 'Nuevo Producto'}</Text>
            
            <TextInput
              style={productsStyles.input}
              placeholder="Nombre"
              value={formData.name}
              onChangeText={(text) => setFormData({ ...formData, name: text })}
            />
            
            <TextInput
              style={[productsStyles.input, productsStyles.textArea]}
              placeholder="Descripción"
              value={formData.description}
              onChangeText={(text) => setFormData({ ...formData, description: text })}
              multiline
              numberOfLines={2}
            />

            <TextInput
              style={productsStyles.input}
              placeholder="Precio"
              value={formData.price}
              onChangeText={(text) => setFormData({ ...formData, price: text })}
              keyboardType="decimal-pad"
            />

            <TextInput
              style={productsStyles.input}
              placeholder="Stock"
              value={formData.stock}
              onChangeText={(text) => setFormData({ ...formData, stock: text })}
              keyboardType="number-pad"
            />

            <View style={productsStyles.pickerContainer}>
              <Text style={productsStyles.label}>Categoría:</Text>
              <Picker
                selectedValue={formData.categoryId}
                onValueChange={(value: string) => setFormData({ ...formData, categoryId: value, subcategoryId: '' })}
                style={productsStyles.picker}
              >
                <Picker.Item label="Seleccione categoría" value="" />
                {(categories || []).map((cat) => {
                  if (!cat || !cat.id || !cat.name) return null;
                  return (
                    <Picker.Item key={cat.id} label={cat.name} value={cat.id.toString()} />
                  );
                })}
              </Picker>
            </View>

            <View style={productsStyles.pickerContainer}>
              <Text style={productsStyles.label}>Subcategoría:</Text>
              <Picker
                selectedValue={formData.subcategoryId}
                onValueChange={(value: string) => setFormData({ ...formData, subcategoryId: value })}
                style={productsStyles.picker}
                enabled={(filteredSubcategories || []).length > 0}
              >
                <Picker.Item label="Seleccione subcategoría" value="" />
                {(filteredSubcategories || []).map((sub) => {
                  if (!sub || !sub.id || !sub.name) return null;
                  return (
                    <Picker.Item key={sub.id} label={sub.name} value={sub.id.toString()} />
                  );
                })}
              </Picker>
            </View>

            <View style={productsStyles.modalActions}>
              <TouchableOpacity style={productsStyles.cancelButton} onPress={() => setModalVisible(false)}>
                <Text style={productsStyles.cancelButtonText}>Cancelar</Text>
              </TouchableOpacity>
              <TouchableOpacity style={productsStyles.saveButton} onPress={handleSave}>
                <Text style={productsStyles.saveButtonText}>Guardar</Text>
              </TouchableOpacity>
            </View>
          </View>
        </View>
      </Modal>
    </View>
  );
}
